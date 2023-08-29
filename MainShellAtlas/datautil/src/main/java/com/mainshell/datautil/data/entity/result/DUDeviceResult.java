package com.mainshell.datautil.data.entity.result;

/**
 * Created by dengzhimin on 2016/6/1.
 */
public class DUDeviceResult {

    private boolean isActived;
    private String error;
    private long activeTime;
    private String activeCode;
    //private String macAddress;
    private String activeState;
    private String deviceState;

    public DUDeviceResult(boolean isActived,
                          String error,
                          long activeTime,
                          String activeCode,
                          //String macAddress,
                          String activeState,
                          String deviceState) {
        this.isActived = isActived;
        this.error = error;
        this.activeTime = activeTime;
        this.activeCode = activeCode;
        //this.macAddress = macAddress;
        this.activeState = activeState;
        this.deviceState = deviceState;
    }

    public boolean isActived() {
        return isActived;
    }

    public void setActived(boolean actived) {
        isActived = actived;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(int activeTime) {
        this.activeTime = activeTime;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

//    public String getMacAddress() {
//        return macAddress;
//    }
//
//    public void setMacAddress(String macAddress) {
//        this.macAddress = macAddress;
//    }

    public String getActiveState() {
        return activeState;
    }

    public void setActiveState(String activeState) {
        this.activeState = activeState;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }
}
