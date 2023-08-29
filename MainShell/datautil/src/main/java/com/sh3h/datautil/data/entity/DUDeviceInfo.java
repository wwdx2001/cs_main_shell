package com.sh3h.datautil.data.entity;


public class DUDeviceInfo extends DURequest {
    /**
     * mac address
     */
    private String macAddress;

    /**
     * device id
     */
    private String deviceID;

    public DUDeviceInfo() {
    }

    public DUDeviceInfo(String macAddress, String deviceID) {
        this.macAddress = macAddress;
        this.deviceID = deviceID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
