package com.mainshell.datautil.data.entity;


public class DUApkConfigResult extends DUResponse {
    private String mComponentName;
    private int mId;
    private String mFunctionName;
    private String mIconName;
    private String mPackageName;
    private String mActivityName;
    private String mParamsName;

    public DUApkConfigResult() {
    }

    public String getComponentName() {
        return mComponentName;
    }

    public void setComponentName(String componentName) {
        this.mComponentName = componentName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getFunctionName() {
        return mFunctionName;
    }

    public void setFunctionName(String functionName) {
        this.mFunctionName = functionName;
    }

    public String getIconName() {
        return mIconName;
    }

    public void setIconName(String iconName) {
        this.mIconName = iconName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public String getActivityName() {
        return mActivityName;
    }

    public void setActivityName(String activityName) {
        this.mActivityName = activityName;
    }

    public String getParamsName() {
        return mParamsName;
    }

    public void setParamsName(String paramsName) {
        this.mParamsName = paramsName;
    }
}
