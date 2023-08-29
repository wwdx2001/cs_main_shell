package com.sh3h.mainshell.ui.installation;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.mainshell.datautil.data.DataManager;
import com.mainshell.datautil.data.entity.DUApkInfoResultEx;
import com.mainshell.datautil.data.entity.DUUpdateXmlFile;
import com.mainshell.datautil.data.entity.base.DUEntitiesResult;
import com.mainshell.datautil.data.entity.result.DUApkInfoResult;
import com.mainshell.datautil.data.local.config.ConfigHelper;
import com.mainshell.datautil.data.local.preference.PreferencesHelper;
import com.mainshell.datautil.data.local.preference.UserSession;
import com.mainshell.datautil.data.local.xml.XmlHelper;
import com.mainshell.datautil.util.NetworkUtil;
import com.sh3h.mainshell.ui.base.ParentPresenter;
import com.sh3h.mainshell.util.DeviceUtil;
import com.sh3h.mainshell.util.SystemUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

//import com.morgoo.droidplugin.pm.PluginManager;
//import com.mainshell.datautil.data.entity.DUApkFile;


/**
 * Created by xulongjun on 2016/5/18.
 */
public class ApkInstallationPresenter extends ParentPresenter<ApkInstallationMvpView> {
    private static final String TAG = "ApkInstallationPresenter";
    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;
    private final XmlHelper mXmlHelper;

    @Inject
    public ApkInstallationPresenter(DataManager dataManager,
                                    PreferencesHelper preferencesHelper,
                                    ConfigHelper configHelper,
                                    XmlHelper xmlHelper) {
        super(dataManager);
        mPreferencesHelper = preferencesHelper;
        mConfigHelper = configHelper;
        mXmlHelper = xmlHelper;
    }

    public File getApksFolderPath() {
        return mConfigHelper.getApksFolderPath();
    }

    public boolean isAtlasMode() {
        return mConfigHelper.isAtlasMode();
    }

    /**
     * get the list of apks
     *
     * @param context
     */
    public void getApkList(Context context) {
        LogUtil.i(TAG, "---getApkList 1---");
        if (!NetworkUtil.isNetworkConnected(context)) {
            LogUtil.i(TAG, "---getApkList 2---");
            getMvpView().onError(ApkInstallationMvpView.ErrorCode.NO_NETWORK);
            return;
        }

        int userId;
        UserSession userSession = mPreferencesHelper.getUserSession();
        userId = userSession.getUserId();
        String deviceId = DeviceUtil.getDeviceID(context);

        if (TextUtil.isNullOrEmpty(deviceId)) {
            LogUtil.i(TAG, "---getApkList 3---");
            getMvpView().onError(ApkInstallationMvpView.ErrorCode.PARAM_INVALID);
            return;
        }

        // the package name of main shell
        String packageName = null;
        PackageInfo packageInfo = SystemUtil.getPackageInfo(context);
        if (packageInfo != null) {
            packageName = packageInfo.packageName;
        }

        final String finalPackageName = TextUtil.getString(packageName);
        mSubscription.add(mDataManager.getApkList(userId, deviceId)
                .filter(new Func1<DUEntitiesResult<DUApkInfoResult>, Boolean>() {
                    @Override
                    public Boolean call(DUEntitiesResult<DUApkInfoResult> duEntitiesResult) {
                        int code = duEntitiesResult.getCode();
                        List<DUApkInfoResult> duApkInfoResultList = duEntitiesResult.getData();
                        return (code == DUEntitiesResult.SUCCESS_CODE)
                                && (duApkInfoResultList != null)
                                && (duApkInfoResultList.size() > 0);
                    }
                })
                .map(new Func1<DUEntitiesResult<DUApkInfoResult>, List<DUApkInfoResultEx>>() {
                    @Override
                    public List<DUApkInfoResultEx> call(DUEntitiesResult<DUApkInfoResult> duEntitiesResult) {
                        List<DUApkInfoResultEx> duApkInfoResultExList =
                                convertToApkInfoList(duEntitiesResult.getData());
                        checkInstalledApks(duApkInfoResultExList, finalPackageName);
                        return duApkInfoResultExList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<DUApkInfoResultEx>>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "----getApkList: onCompleted----");
                        getMvpView().onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "----getApkList: onError----");
                        getMvpView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DUApkInfoResultEx> duApkInfoResultExList) {
                        LogUtil.i(TAG, "----getApkList: onNext----");
                        getMvpView().onGetApkList(duApkInfoResultExList);
                    }
                }));
    }

    /**
     * convert one object to another
     *
     * @param duApkInfoResultList
     * @return
     */
    private List<DUApkInfoResultEx> convertToApkInfoList(List<DUApkInfoResult> duApkInfoResultList) {
        List<DUApkInfoResultEx> duApkInfoResultExList = new ArrayList<>();
        for (DUApkInfoResult duApkInfoResult : duApkInfoResultList) {
            if (TextUtil.isNullOrEmpty(duApkInfoResult.getAppName())
                    || TextUtil.isNullOrEmpty(duApkInfoResult.getAppId())
                    || TextUtil.isNullOrEmpty(duApkInfoResult.getPackageName())
                    || TextUtil.isNullOrEmpty(duApkInfoResult.getIconUrl())
                    || TextUtil.isNullOrEmpty(duApkInfoResult.getAppUrl())
//                    || TextUtil.isNullOrEmpty(duApkInfoResult.getConfigUrl())
                    || TextUtil.isNullOrEmpty(duApkInfoResult.getDataUrl())) {
                continue;
            }

            duApkInfoResult.setAppName(duApkInfoResult.getAppName().trim());
            DUApkInfoResultEx duApkInfoResultEx = new DUApkInfoResultEx(duApkInfoResult);
            duApkInfoResultExList.add(duApkInfoResultEx);
        }

        return duApkInfoResultExList;
    }

    /**
     * check if the apks has been installed
     *
     * @param duApkInfoResultExList
     */
    private void checkInstalledApks(List<DUApkInfoResultEx> duApkInfoResultExList,
                                    String currentPackageName) {
        LogUtil.i(TAG, "---checkInstalledApks 1---");
        DUUpdateXmlFile duUpdateXmlFile = mXmlHelper.getDuUpdateXmlFile();
        if ((duUpdateXmlFile == null)
                || (duUpdateXmlFile.getApplicationList() == null)
                || (duUpdateXmlFile.getApplicationList().size() <= 0)) {
            LogUtil.i(TAG, "---checkInstalledApks 2---");
            for (DUApkInfoResultEx duApkInfoResultEx : duApkInfoResultExList) {
                duApkInfoResultEx.setState(DUApkInfoResultEx.STATE_DOWNLOAD);
            }
        } else {
            LogUtil.i(TAG, "---checkInstalledApks 3---");
            List<DUUpdateXmlFile.Application> applicationList = duUpdateXmlFile.getApplicationList();
            for (DUApkInfoResultEx duApkInfoResultEx : duApkInfoResultExList) {
                duApkInfoResultEx.setState(DUApkInfoResultEx.STATE_DOWNLOAD);
                for (DUUpdateXmlFile.Application application : applicationList) {
                    if ((!application.getAppId().equals(duApkInfoResultEx.getAppId()))
                            || (!application.getPackageName().equals(duApkInfoResultEx.getPackageName()))
                            || (!application.isInstalled())) {
                        // main shell
                        if (duApkInfoResultEx.getPackageName().equals(currentPackageName)
                                && application.getPackageName().equals(currentPackageName)) {
                            application.setInstalled(true);
                        } else {
                            continue;
                        }
                    }

                    if (application.getVersionCode() >= duApkInfoResultEx.getVersionCode()) {
                        duApkInfoResultEx.setState(DUApkInfoResultEx.STATE_INSTALLED);
                    } else {
                        duApkInfoResultEx.setState(DUApkInfoResultEx.STATE_UPDATE);
                    }
                    break;
                }
            }
        }
    }
}
