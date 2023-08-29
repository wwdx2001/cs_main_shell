package com.sh3h.datautil.data.entity.result;

/**
 * Created by dengzhimin on 2016/5/31.
 */
public class DULoginResult {
    private int userId;
    private String accessToken;

    public DULoginResult(int userId, String accessToken){
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
