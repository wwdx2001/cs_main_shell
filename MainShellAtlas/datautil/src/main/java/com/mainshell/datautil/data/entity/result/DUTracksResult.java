package com.mainshell.datautil.data.entity.result;

/**
 * 轨迹
 * Created by dengzhimin on 2016/6/1.
 */
public class DUTracksResult {

    private int time;//采集时间
    private String locationType;//坐标类型
    private float x;//经度
    private float y;//纬度

    public DUTracksResult(int time, String locationType, float x, float y){
        this.time = time;
        this.locationType = locationType;
        this.x = x;
        this.y = y;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
