package com.mainshell.datautil.data.entity.result;

import java.util.List;

/**
 * Created by xulongjun on 2016/6/2.
 */
public class DUApkInfoResult {
    private String appName;
    private String appId;
    private String packageName;
    private int versionCode;
    private String versionName;
    private String iconUrl;
    private String appUrl;
    private String dataUrl;

    public DUApkInfoResult() {
    }

    public DUApkInfoResult(String appName,
                           String appId,
                           String packageName,
                           int versionCode,
                           String versionName,
                           String iconUrl,
                           String appUrl,
                           String dataUrl) {
        this.appName = appName;
        this.appId = appId;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.iconUrl = iconUrl;
        this.appUrl = appUrl;
        //this.configUrl = configUrl;
        this.dataUrl = dataUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

}
