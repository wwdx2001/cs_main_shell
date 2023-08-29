package com.mainshell.datautil.data.entity;


public class DULoginInfo extends DURequest {
    private String account;
    private String pwd;
    private String appIdentify;

    public DULoginInfo() {

    }

    public DULoginInfo(String account,
                       String pwd,
                       String appIdentify) {
        this.account = account;
        this.pwd = pwd;
        this.appIdentify = appIdentify;
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
}
