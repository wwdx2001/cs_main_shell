package com.sh3h.mainshell_chaobiaoji.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sh3h.datautil.data.entity.DUUpdateXmlFile;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.service.FileService;
//import com.sh3h.mainshell.event.countly.CountlyEvent;
import com.sh3h.mainshell_chaobiaoji.util.SystemUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

//import ly.count.android.sdk.Countly;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

public class ParentActivity extends BaseActivity implements SwipeBackActivityBase {
    private static final String TAG = "ParentActivity";
    private static final String RESTORING_STATE = "restoringState";
    private static final String MAIN_SHELL_APP_NAME = "MainShell";

    private SwipeBackActivityHelper mHelper;
    protected boolean needRefresh;
    protected boolean isActive;

    public ParentActivity() {
        super();

        needRefresh = false;
        isActive = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBothAnimation();

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        MainApplication.get(this).initGpsLocation();
        MainApplication.get(this).bindFileService();
        MainApplication.get(this).initMonitor();
        MainApplication.get(this).startPush();
        MainApplication.get(this).startKeepAlive();
        MainApplication.get(this).startBaiduTrack();

        if (savedInstanceState != null) {
            boolean restoringState = savedInstanceState.getBoolean(RESTORING_STATE, false);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
        MainApplication.get(this).add2WhiteList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if ((v == null) && (mHelper != null)) {
            return mHelper.findViewById(id);
        }
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    protected void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                if (MainApplication.get(this).isMonitorInit()) {
                    switch (MainApplication.get(this).getMonitorType()) {
                        case NONE:
                            break;
                        case COUTLY:
                            //Countly.sharedInstance().recordEvent(CountlyEvent.BACK, 1);
                            break;
                        case UMENG:
                            //MobclickAgent.onEvent(this, "back");
                            break;
                        default:
                            break;
                    }
                }
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RESTORING_STATE, true);
    }

    protected void setActionBarBackButtonEnable() {
        ActionBar actionBar = getSupportActionBar();
        //设置ActionBar左边默认的图标是否可用
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setHomeButtonEnabled(true);
    }

    protected void setActionBarSubTitle(String title) {
        if (!TextUtil.isNullOrEmpty(title)) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setSubtitle(title);
        }
    }

    /**
     * download apk & config & data
     *
     * @param appName
     * @param appId
     * @param packageName
     * @param versionCode
     * @param versionName
     * @param appUrl
     * @param dataUrl
     */
    protected void downloadFiles(String appName,
                                 String appId,
                                 String packageName,
                                 int versionCode,
                                 String versionName,
                                 String appUrl,
                                 String dataUrl,
                                 boolean isDownloadingNewly) {
        if (TextUtil.isNullOrEmpty(appName)
                || TextUtil.isNullOrEmpty(appId)
                || TextUtil.isNullOrEmpty(packageName)
                || TextUtil.isNullOrEmpty(versionName)
                || TextUtil.isNullOrEmpty(appUrl)
                || TextUtil.isNullOrEmpty(dataUrl)) {
            return;
        }

        Intent intent = FileService.getStartIntent(this);
        intent.putExtra(FileService.FILTER_TYPE, FileService.FilterType.APK.ordinal());
        intent.putExtra(FileService.APP_NAME, appName);
        intent.putExtra(FileService.APP_ID, appId);
        intent.putExtra(FileService.PACKAGE_NAME, packageName);
        intent.putExtra(FileService.VERSION_CODE, versionCode);
        intent.putExtra(FileService.VERSION_NAME, versionName);
        intent.putExtra(FileService.APP_URL, appUrl);
        intent.putExtra(FileService.DATA_URL, dataUrl);
        intent.putExtra(FileService.NEED_DOWNLOAD_OTHER_FILES, true);
        intent.putExtra(FileService.IS_DOWNLOADING_NEWLY, isDownloadingNewly);
        MainApplication.get(this).starFileService(intent);
    }

    protected boolean updateFile(FileService.FilterType filterType, String appName, String appId,
                              String packageName, int versionCode, String versionName) {
        if (TextUtil.isNullOrEmpty(appName)
                || TextUtil.isNullOrEmpty(appId)
                || TextUtil.isNullOrEmpty(packageName)
                || TextUtil.isNullOrEmpty(versionName)) {
            return false;
        }

        Intent intent = FileService.getStartIntent(this);
        intent.putExtra(FileService.FILTER_TYPE, filterType.ordinal());
        intent.putExtra(FileService.APP_NAME, appName);
        intent.putExtra(FileService.APP_ID, appId);
        intent.putExtra(FileService.PACKAGE_NAME, packageName);
        intent.putExtra(FileService.VERSION_CODE, versionCode);
        intent.putExtra(FileService.VERSION_NAME, versionName);
        intent.putExtra(FileService.NEED_DOWNLOAD_OTHER_FILES, false);
        MainApplication.get(this).starFileService(intent);
        return true;
    }

    /**
     * update all files of apk & config & data
     *
     * @param duUpdateXmlFile
     */
    protected int updateFileList(DUUpdateXmlFile duUpdateXmlFile) {
        boolean result = false;
        int count = 0;
        PackageInfo packageInfo = SystemUtil.getPackageInfo(MainApplication.get(this));
        if (packageInfo != null) { // mainshell apk
            result = updateFile(FileService.FilterType.APK, MAIN_SHELL_APP_NAME,
                    packageInfo.packageName, packageInfo.packageName,
                    packageInfo.versionCode + 1, packageInfo.versionName);
            if (result) {
                count++;
            }
        }

        if ((duUpdateXmlFile == null)
                || (duUpdateXmlFile.getApplicationList() == null)
                || (duUpdateXmlFile.getApplicationList().size() <= 0)) {
            if (packageInfo != null) { // mainshell data
                result = updateFile(FileService.FilterType.DATA, MAIN_SHELL_APP_NAME,
                        packageInfo.packageName, packageInfo.packageName,
                        1, "1.0.0");
                if (result) {
                    count++;
                }
            }
            return count;
        }

        List<DUUpdateXmlFile.Application> applicationList = duUpdateXmlFile.getApplicationList();
        for (DUUpdateXmlFile.Application application : applicationList) {
            if (((packageInfo != null)
                    && (packageInfo.packageName.equals(application.getPackageName())))) {
                // mainshell data
                DUUpdateXmlFile.Data data = application.getData();
                if (data != null) {
                    result = updateFile(FileService.FilterType.DATA, application.getAppName(),
                            application.getAppId(), application.getPackageName(),
                            data.getVersion() + 1, application.getVersionName());
                } else {
                    result = updateFile(FileService.FilterType.DATA, MAIN_SHELL_APP_NAME,
                            packageInfo.packageName, packageInfo.packageName,
                            1, "1.0.0");
                }
                if (result) {
                    count++;
                }
                continue;
            } else if ((!application.isInstalled())) {
                continue;
            }

            result = updateFile(FileService.FilterType.APK, application.getAppName(),
                    application.getAppId(), application.getPackageName(),
                    application.getVersionCode() + 1, application.getVersionName());
            if (result) {
                count++;
            }

            DUUpdateXmlFile.Data data = application.getData();
            if (data != null) {
                result = updateFile(FileService.FilterType.DATA, application.getAppName(),
                        application.getAppId(), application.getPackageName(),
                        data.getVersion() + 1, application.getVersionName());
                if (result) {
                    count++;
                }
            }
        }

        return count;
    }
}
