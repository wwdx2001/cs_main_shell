package com.sh3h.datautil.data.entity;


import com.sh3h.datautil.data.entity.result.DUApkInfoResult;

public class DUApkInfoResultEx extends DUApkInfoResult {
    public static final int STATE_UNKNOWN = 0;      // 未知
    public static final int STATE_DOWNLOAD = 1;     // 下载
    public static final int STATE_INSTALLED = 2;   // 已安装
    public static final int STATE_UPDATE = 3;      // 更新

    private int state;
    private String localPath;
    private boolean needDownloadApk;
    private boolean isProgressbarVisible;
    private int percent;

    public DUApkInfoResultEx() {
    }

    public DUApkInfoResultEx(DUApkInfoResult duApkInfoResult) {
        super(duApkInfoResult.getAppName(),
                duApkInfoResult.getAppId(),
                duApkInfoResult.getPackageName(),
                duApkInfoResult.getVersionCode(),
                duApkInfoResult.getVersionName(),
                duApkInfoResult.getIconOuterUrl(),
                duApkInfoResult.getAppOuterUrl(),
                duApkInfoResult.getDataOuterUrl(),
                duApkInfoResult.getIconInnerUrl(),
                duApkInfoResult.getAppInnerUrl(),
                duApkInfoResult.getDataInnerUrl());

        this.state = STATE_UNKNOWN;
        this.localPath = null;
        this.needDownloadApk = false;
        this.isProgressbarVisible = false;
        this.percent = 0;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public boolean isNeedDownloadApk() {
        return needDownloadApk;
    }

    public void setNeedDownloadApk(boolean needDownloadApk) {
        this.needDownloadApk = needDownloadApk;
    }

    public boolean isProgressbarVisible() {
        return isProgressbarVisible;
    }

    public void setProgressbarVisible(boolean progressbarVisible) {
        isProgressbarVisible = progressbarVisible;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
