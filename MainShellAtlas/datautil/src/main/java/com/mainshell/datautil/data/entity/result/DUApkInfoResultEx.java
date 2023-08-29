package com.mainshell.datautil.data.entity.result;

@Deprecated
public class DUApkInfoResultEx extends DUApkInfoResult {
    public static final int STATE_UNKNOWN = 0;      // 未知
    public static final int STATE_UNINSTALLED = 1; // 未安装
    public static final int STATE_INSTALLED = 2;   // 已安装
    public static final int STATE_UPDATE = 3;      // 更新

    private int state;
    private String localPath;
    private boolean needDownloadApk;
    private boolean isVisible;

    public DUApkInfoResultEx() {
    }

//    public DUApkInfoResultEx(DUApkInfoResult duApkInfoResult) {
//        super(duApkInfoResult.getAppName(),
//                duApkInfoResult.getAppId(),
//                duApkInfoResult.getVer(),
//                duApkInfoResult.getIconUrl(),
//                duApkInfoResult.getAppUrl());
//
//        this.state = STATE_UNKNOWN;
//        this.localPath = null;
//        this.needDownloadApk = false;
//        this.isVisible = false;
//    }

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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
