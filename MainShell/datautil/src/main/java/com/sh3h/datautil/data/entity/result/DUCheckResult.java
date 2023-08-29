package com.sh3h.datautil.data.entity.result;

/**
 * Created by xulongjun on 2016/6/8.
 */
public class DUCheckResult {
    private int ver;
    private String innerUrl;
    private String outerUrl;
    private String role;

    public DUCheckResult() {
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public String getInnerUrl() {
        return innerUrl;
    }

    public void setInnerUrl(String innerUrl) {
        this.innerUrl = innerUrl;
    }

    public String getOuterUrl() {
        return outerUrl;
    }

    public void setOuterUrl(String outerUrl) {
        this.outerUrl = outerUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
