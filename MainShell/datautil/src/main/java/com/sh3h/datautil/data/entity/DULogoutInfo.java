package com.sh3h.datautil.data.entity;



public class DULogoutInfo extends DURequest {
    private int userId;
    private String deviceId;

    public DULogoutInfo() {
    }

    public DULogoutInfo(int userId, String deviceId) {
        this.userId = userId;
        this.deviceId = deviceId;
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
}
