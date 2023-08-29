package com.sh3h.datautil.data.entity.result;



public class DUTokenResult {
    public static final int TOKEN_STATE_VALID = 1;
    public static final int TOKEN_STATE_UPDATE = 2;
    public static final int TOKEN_STATE_EXIT = 3;

    private int tokenState; // 1: accessToken有效; 2: accessToken更新; 3: 退出系统
    private String accessToken;

    public DUTokenResult(int tokenState, String accessToken) {
        this.tokenState = tokenState;
        this.accessToken = accessToken;
    }

    public int getTokenState() {
        return tokenState;
    }

    public void setTokenState(int tokenState) {
        this.tokenState = tokenState;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
