package com.sh3h.datautil.data.entity.result;

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
    private String iconOuterUrl;
    private String appOuterUrl;
    private String dataOuterUrl;
    private String iconInnerUrl;
    private String appInnerUrl;
    private String dataInnerUrl;

    public DUApkInfoResult() {
    }

    public DUApkInfoResult(String appName, String appId, String packageName, int versionCode, String versionName,
                           String iconOuterUrl, String appOuterUrl, String dataOuterUrl,
                           String iconInnerUrl, String appInnerUrl, String dataInnerUrl) {
        this.appName = appName;
        this.appId = appId;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.iconOuterUrl = iconOuterUrl;
        this.appOuterUrl = appOuterUrl;
        this.dataOuterUrl = dataOuterUrl;
        this.iconInnerUrl = iconInnerUrl;
        this.appInnerUrl = appInnerUrl;
        this.dataInnerUrl = dataInnerUrl;
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

    public String getIconOuterUrl() {
        return iconOuterUrl;
    }

    public void setIconOuterUrl(String iconOuterUrl) {
        this.iconOuterUrl = iconOuterUrl;
    }

    public String getAppOuterUrl() {
        return appOuterUrl;
    }

    public void setAppOuterUrl(String appOuterUrl) {
        this.appOuterUrl = appOuterUrl;
    }

    public String getDataOuterUrl() {
        return dataOuterUrl;
    }

    public void setDataOuterUrl(String dataOuterUrl) {
        this.dataOuterUrl = dataOuterUrl;
    }

    public String getIconInnerUrl() {
        return iconInnerUrl;
    }

    public void setIconInnerUrl(String iconInnerUrl) {
        this.iconInnerUrl = iconInnerUrl;
    }

    public String getAppInnerUrl() {
        return appInnerUrl;
    }

    public void setAppInnerUrl(String appInnerUrl) {
        this.appInnerUrl = appInnerUrl;
    }

    public String getDataInnerUrl() {
        return dataInnerUrl;
    }

    public void setDataInnerUrl(String dataInnerUrl) {
        this.dataInnerUrl = dataInnerUrl;
    }
}
