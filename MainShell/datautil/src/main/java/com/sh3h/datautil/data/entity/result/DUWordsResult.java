package com.sh3h.datautil.data.entity.result;

/**
 * Created by xulongjun on 2016/7/4.
 */
public class DUWordsResult {

    //词语Id
    private int wId;
    //词语文本
    private String wName;
    //词语值
    private String wValue;
    //词语值扩展内容
    private String wValueEx;
    //词语分组
    private String wGroup;
    //父节点id
    private int wParentId;
    //备注
    private String wRemark;

    public DUWordsResult() {
    }

    public DUWordsResult(int wId, String wName, String wValue, String wValueEx, String wGroup, int wParentId, String wRemark) {
        this.wId = wId;
        this.wName = wName;
        this.wValue = wValue;
        this.wValueEx = wValueEx;
        this.wGroup = wGroup;
        this.wParentId = wParentId;
        this.wRemark = wRemark;
    }

    public int getWid() {
        return wId;
    }

    public void setWid(int wId) {
        this.wId = wId;
    }

    public String getwName() {
        return wName;
    }

    public void setwName(String wName) {
        this.wName = wName;
    }

    public String getwValue() {
        return wValue;
    }

    public void setwValue(String wValue) {
        this.wValue = wValue;
    }

    public String getwValueEx() {
        return wValueEx;
    }

    public void setwValueEx(String wValueEx) {
        this.wValueEx = wValueEx;
    }

    public String getwGroup() {
        return wGroup;
    }

    public void setwGroup(String wGroup) {
        this.wGroup = wGroup;
    }

    public int getwParentId() {
        return wParentId;
    }

    public void setwParentId(int wParentId) {
        this.wParentId = wParentId;
    }

    public String getwRemark() {
        return wRemark;
    }

    public void setwRemark(String wRemark) {
        this.wRemark = wRemark;
    }
}
