package com.sh3h.datautil.data.entity;


public class DULoginInfo extends DURequest {
    private String account;
    private String pwd;
    private String appIdentify;
    private String deviceId;

    public DULoginInfo() {

    }

    public DULoginInfo(String account,
                       String pwd,
                       String appIdentify,
                       String deviceId) {
        this.account = account;
        this.pwd = pwd;
        this.appIdentify = appIdentify;
        this.deviceId = deviceId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAppIdentify() {
        return appIdentify;
    }

    public void setAppIdentify(String appIdentify) {
        this.appIdentify = appIdentify;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
