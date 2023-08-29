package com.sh3h.mainshell_chaobiaoji.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.DUFile;
import com.sh3h.datautil.data.entity.DUFileResult;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.event.DataBusEvent;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.datautil.util.NetworkUtil;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mainshell_chaobiaoji.event.UIBusEvent;
import com.sh3h.mainshell_chaobiaoji.util.AndroidComponentUtil;
import com.sh3h.mobileutil.util.ApplicationsUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FileService extends Service {
    public enum FilterType {
        APK,
        DATA
    }

    @Inject
    DataManager mDataManager;

    @Inject
    ConfigHelper mConfigHelper;

    @Inject
    EventPosterHelper mEventPosterHelper;

    @Inject
    Bus mBus;

    public static final String FIRST_TIME = "firstTime";
    public static final String FILTER_TYPE = "filterType";
    public static final String APP_NAME = "appName";
    public static final String APP_ID = "appId";
    public static final String PACKAGE_NAME = "packageName";
    public static final String VERSION_CODE = "versionCode";
    public static final String VERSION_NAME = "versionName";
    public static final String APP_URL = "appUrl";
    //public static final String CONFIG_URL = "configUrl";
    public static final String DATA_URL = "dataUrl";
    public static final String NEED_DOWNLOAD_OTHER_FILES = "needDownloadOtherFiles"; // apk + config + data
    public static final String IS_DOWNLOADING_NEWLY = "isDownloadingNewly";

    private static final String TAG = "FileService";
    private static final int APK_DELTA_PERCENT = 80;
    private static final int CONFIG_DELTA_PERCENT = 10;
    private static final int DATA_DELTA_PERCENT = 10;
    private static final int FULL_PERCENT = 100;

    private Subscription mSubscription;
    private List<DUFile> mDuFileList;
    private DUFile mCurrentDUFile;
    private DUFileResult mCurrentDUFileResult;
    private boolean mIsOperationFinished;
    private boolean parserResult = true;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, FileService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, FileService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MainApplication.get(this).getComponent().inject(this);
        mBus.register(this);
        mDuFileList = new ArrayList<>();
        mCurrentDUFile = null;
        mCurrentDUFileResult = null;
        mIsOperationFinished = true;
        LogUtil.i(TAG, "---onCreate---");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "---onStartCommand---" + (intent != null));
        if (intent == null) {
            return START_STICKY;
        }

        boolean b = intent.getBooleanExtra(FIRST_TIME, false);
        if (b) {
            LogUtil.i(TAG, "---onStartCommand first time---");
            return START_STICKY;
        }

        if (!NetworkUtil.isNetworkConnected(this)) {
            ApplicationsUtil.showMessage(this, R.string.text_network_not_connected);
            stopSelf(startId);
            LogUtil.i(TAG, "---onStartCommand---network isn't connected");
            return START_NOT_STICKY;
        }

        add2DUFileList(intent);
        processDUFile();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(TAG, "---onBind---");
        return new FileServiceBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.i(TAG, "---onUnbind---");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        mBus.unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onFileEnd(UIBusEvent.FileEnd fileEnd) {
        LogUtil.i(TAG, "---onFileEnd---");
        processDUFile();
    }

    @Subscribe
    public void onFileResult(DataBusEvent.FileResult fileResult) {
        LogUtil.i(TAG, "---onFileResult---");
        if (mCurrentDUFile != null) {
            if (mCurrentDUFile.isNeedDownloadOtherFiles()) {
                switch (mCurrentDUFile.getFileType()) {
                    case APK:
                        downloadDataFile();
                        break;
                    case DATA:
                        postEndEvent(mCurrentDUFile, fileResult.isSuccess(), null);
                        break;
                }
            } else {
                postEndEvent(mCurrentDUFile, fileResult.isSuccess(), null);
            }
        }
    }

    public DUFileResult getDUFileResult() {
        return mCurrentDUFileResult;
    }

    public boolean isOperationFinished() {
        return mIsOperationFinished;
    }

    private void add2DUFileList(Intent intent) {
        LogUtil.i(TAG, "---add2DUFileList 1---");

        // check the type
        int type = intent.getIntExtra(FILTER_TYPE, 0);
        FilterType filterType;
        try {
            filterType = FilterType.values()[type];
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // convert to file type
        DUFile.FileType fileType;
        switch (filterType) {
            case APK:
                fileType = DUFile.FileType.APK;
                break;
            case DATA:
                fileType = DUFile.FileType.DATA;
                break;
            default:
                LogUtil.i(TAG, "---param is error---");
                return;
        }

        // get other params
        String appName = intent.getStringExtra(APP_NAME);
        String appId = intent.getStringExtra(APP_ID);
        String packageName = intent.getStringExtra(PACKAGE_NAME);
        int versionCode = intent.getIntExtra(VERSION_CODE, 0);
        String versionName = intent.getStringExtra(VERSION_NAME);
        String appUrl = intent.getStringExtra(APP_URL);
        //String configUrl = intent.getStringExtra(CONFIG_URL);
        String dataUrl = intent.getStringExtra(DATA_URL);
        boolean needDownloadOtherFiles = intent.getBooleanExtra(NEED_DOWNLOAD_OTHER_FILES, false);
        boolean isDownloadingNewly = intent.getBooleanExtra(IS_DOWNLOADING_NEWLY, false);

        // check the params
        if (TextUtil.isNullOrEmpty(appName)
                || TextUtil.isNullOrEmpty(appId)
                || TextUtil.isNullOrEmpty(packageName)) {
            LogUtil.i(TAG, "---add2DUFileList 2---");
            return;
        }

        if (mDuFileList == null) {
            mDuFileList = new ArrayList<>();
        }

        for (DUFile duFile : mDuFileList) {
            if (duFile.getAppName().equals(appName)
                    && duFile.getAppId().equals(appId)
                    && duFile.getPackageName().equals(packageName)
                    && duFile.getVersionCode() == versionCode
                    && duFile.getFileType() == fileType) {
                LogUtil.i(TAG, "---add2DUFileList 3---");
                return;
            }
        }

        mDuFileList.add(new DUFile(fileType, appName, appId, packageName,
                versionCode, versionName, appUrl, null, needDownloadOtherFiles, isDownloadingNewly));
    }

    private void processDUFile() {
        LogUtil.i(TAG, "---processDUFile 1---");
        if (mIsOperationFinished && (mDuFileList.size() > 0)) {
            LogUtil.i(TAG, "---processDUFile 2---");
            mCurrentDUFile = mDuFileList.remove(0);
            postStartEvent(mCurrentDUFile);
            switch (mCurrentDUFile.getFileType()) {
                case APK:
                    downloadApkFile();
                    break;
                case DATA:
                    downloadDataFile();
                    break;
                default:
                    postEndEvent(mCurrentDUFile, false, "the type of a file is error");
                    break;
            }
        }
    }

    /**
     * download an apk file
     */
    private void downloadApkFile() {
        LogUtil.i(TAG, "---downloadApkFile 1---");

        if ((mCurrentDUFile == null) ||
                TextUtil.isNullOrEmpty(mCurrentDUFile.getAppName())
                || TextUtil.isNullOrEmpty(mCurrentDUFile.getAppId())
                || TextUtil.isNullOrEmpty(mCurrentDUFile.getPackageName())) {
            LogUtil.i(TAG, "---downloadApkFile 2---");
            postEndEvent(mCurrentDUFile, false, "downloadApkFile is error");
            return;
        }

        if ((mSubscription != null) && (!mSubscription.isUnsubscribed())) {
            mSubscription.unsubscribe();
        }

        mSubscription = mDataManager.downloadApkFile(mCurrentDUFile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUFileResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---downloadApkFile onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, String.format("---downloadApkFile onError: %s---", e.getMessage()));
                        postEndEvent(mCurrentDUFile, false, e.getMessage());
                    }

                    @Override
                    public void onNext(DUFileResult duFileResult) {
                        int percent = (int) (duFileResult.getPercent() * APK_DELTA_PERCENT * 1.0 / FULL_PERCENT);
                        percent = (percent > APK_DELTA_PERCENT ? APK_DELTA_PERCENT : percent);
                        duFileResult.setPercent(percent);
                        LogUtil.i(TAG, "---downloadApkFile: onNext---" + duFileResult.getPercent());
                        postStepEvent(duFileResult);
                    }
                });
    }

    /**
     * download the data file
     */
    private void downloadDataFile() {
        LogUtil.i(TAG, "---downloadDataFile start---");

        if ((mCurrentDUFile == null) ||
                TextUtil.isNullOrEmpty(mCurrentDUFile.getAppName())
                || TextUtil.isNullOrEmpty(mCurrentDUFile.getAppId())
                || TextUtil.isNullOrEmpty(mCurrentDUFile.getPackageName())) {
            LogUtil.i(TAG, "---downloadDataFile 2---");
            postEndEvent(mCurrentDUFile, false, "downloadDataFile is error");
            return;
        }

        if ((mSubscription != null) && (!mSubscription.isUnsubscribed())) {
            mSubscription.unsubscribe();
        }

        if (mCurrentDUFile.isNeedDownloadOtherFiles()) {
            mCurrentDUFile.setFileType(DUFile.FileType.DATA);
            mCurrentDUFile.setVersionCode(DUFile.DEFAULT_VERSION_CODE);
        }

        mSubscription = mDataManager.downloadDataFile(mCurrentDUFile)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<DUFileResult>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "---downloadDataFile onCompleted---");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, String.format("---downloadDataFile onError: %s---", e.getMessage()));
                        postEndEvent(mCurrentDUFile, false, e.getMessage());
                    }

                    @Override
                    public void onNext(DUFileResult duFileResult) {
                        int percent = (int) (duFileResult.getPercent() * DATA_DELTA_PERCENT
                                * 1.0 / FULL_PERCENT + (APK_DELTA_PERCENT + CONFIG_DELTA_PERCENT));
                        percent = (percent > FULL_PERCENT ? FULL_PERCENT : percent);
                        duFileResult.setPercent(percent);
                        LogUtil.i(TAG, "---downloadDataFile: onNext---" + duFileResult.getPercent());
                        postStepEvent(duFileResult);
                    }
                });
    }

    /**
     * post a starting event
     *
     * @param duFile
     */
    private void postStartEvent(DUFile duFile) {
        mIsOperationFinished = false;
        mCurrentDUFileResult = new DUFileResult(mCurrentDUFile, 0);
        mEventPosterHelper.postEventSafely(new UIBusEvent.FileStart(
                duFile.getAppName(), duFile.getAppId()));
    }

    /**
     * post the event of every step
     *
     * @param duFileResult
     */
    private void postStepEvent(DUFileResult duFileResult) {
        DUFile duFile = duFileResult.getDuFile();
        if (duFile == null) {
            return;
        }

        if (mCurrentDUFileResult != null) {
            mCurrentDUFileResult.setPercent(duFileResult.getPercent());
        }

        UIBusEvent.FileStep.FileType fileType;
        switch (duFile.getFileType()) {
            case APK:
                fileType = UIBusEvent.FileStep.FileType.APK;
                break;
            case DATA:
                fileType = UIBusEvent.FileStep.FileType.DATA;
                break;
            default:
                return;
        }

        mEventPosterHelper.postEventSafely(new UIBusEvent.FileStep(fileType,
                duFile.getAppName(), duFile.getAppId(), duFileResult.getPercent()));
    }

    /**
     * post the ending event
     *
     * @param duFile
     * @param isSuccess
     * @param message
     */
    private void postEndEvent(DUFile duFile, boolean isSuccess, String message) {
        mIsOperationFinished = true;
        mCurrentDUFileResult = null;
        mEventPosterHelper.postEventSafely(new UIBusEvent.FileEnd(
                duFile.getAppName(), duFile.getAppId(), duFile.getPackageName(),
                duFile.getUrl(), isSuccess, message,
                ((duFile.getFileType() == DUFile.FileType.APK) || duFile.isNeedDownloadOtherFiles())));
    }

    public class FileServiceBinder extends Binder {
        public FileService getService() {
            // Return this instance of FileService so clients can call public methods
            return FileService.this;
        }
    }
}
