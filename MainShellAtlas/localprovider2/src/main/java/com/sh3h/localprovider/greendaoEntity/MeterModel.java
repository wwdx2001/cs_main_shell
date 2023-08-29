package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 水表型号
 */
@Entity(nameInDb = "MR_METERMODEL")
public class MeterModel {
    @Id
    private int MODEL_ID;           //水表型号主键
    @NotNull
    private String MODEL_NAME;      //水表型号简称
    @NotNull
    private String MODEL_BRIEF_NAME;//水表型号全程
    private String MODEL_REMARK;    //水表型号备注
    @Generated(hash = 385957912)
    public MeterModel(int MODEL_ID, @NotNull String MODEL_NAME,
            @NotNull String MODEL_BRIEF_NAME, String MODEL_REMARK) {
        this.MODEL_ID = MODEL_ID;
        this.MODEL_NAME = MODEL_NAME;
        this.MODEL_BRIEF_NAME = MODEL_BRIEF_NAME;
        this.MODEL_REMARK = MODEL_REMARK;
    }
    @Generated(hash = 1631343980)
    public MeterModel() {
    }
    public int getMODEL_ID() {
        return this.MODEL_ID;
    }
    public void setMODEL_ID(int MODEL_ID) {
        this.MODEL_ID = MODEL_ID;
    }
    public String getMODEL_NAME() {
        return this.MODEL_NAME;
    }
    public void setMODEL_NAME(String MODEL_NAME) {
        this.MODEL_NAME = MODEL_NAME;
    }
    public String getMODEL_BRIEF_NAME() {
        return this.MODEL_BRIEF_NAME;
    }
    public void setMODEL_BRIEF_NAME(String MODEL_BRIEF_NAME) {
        this.MODEL_BRIEF_NAME = MODEL_BRIEF_NAME;
    }
    public String getMODEL_REMARK() {
        return this.MODEL_REMARK;
    }
    public void setMODEL_REMARK(String MODEL_REMARK) {
        this.MODEL_REMARK = MODEL_REMARK;
    }
}
