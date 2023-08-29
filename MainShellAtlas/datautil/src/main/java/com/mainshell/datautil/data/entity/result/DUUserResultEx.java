package com.mainshell.datautil.data.entity.result;



public class DUUserResultEx {
    private String accessToken;
    private DUUserResult duUserResult;

    public DUUserResultEx() {
        this.accessToken = null;
        this.duUserResult = null;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public DUUserResult getDuUserResult() {
        return duUserResult;
    }

    public void setDuUserResult(DUUserResult duUserResult) {
        this.duUserResult = duUserResult;
    }
}
