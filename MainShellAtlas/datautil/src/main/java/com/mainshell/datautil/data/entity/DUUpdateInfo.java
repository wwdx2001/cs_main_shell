package com.mainshell.datautil.data.entity;


public class DUUpdateInfo extends DURequest {
    private int appVersion;
    private int dataVersion;

    public DUUpdateInfo() {
        appVersion = 0;
        dataVersion = 0;
    }

    public DUUpdateInfo(int appVersion,
                        int dataVersion) {
        this.appVersion = appVersion;
        this.dataVersion = dataVersion;
        this.duHandler = null;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public int getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(int dataVersion) {
        this.dataVersion = dataVersion;
    }
}
