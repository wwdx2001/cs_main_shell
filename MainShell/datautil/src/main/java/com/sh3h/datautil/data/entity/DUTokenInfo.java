package com.sh3h.datautil.data.entity;



public class DUTokenInfo extends DURequest {
    private int userId;
    private String deviceId;
    private String accessToken;

    public DUTokenInfo() {
    }

    public DUTokenInfo(int userId, String deviceId, String accessToken) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.accessToken = accessToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
