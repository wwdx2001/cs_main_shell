package com.mainshell.datautil.data.entity;

import android.graphics.Bitmap;

/**
 * Created by dengzhimin on 2016/5/30.
 */
@Deprecated
public class DUApkInfo {

    private int id;
    private String name;
    private String packageName;
    private String icon;
    private String activity;
    private String params;

    public DUApkInfo(){

    }

    public DUApkInfo(int id, String name, String packageName, String icon, String activity, String params){
        this.id = id;
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
        this.activity = activity;
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
