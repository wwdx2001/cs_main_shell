package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 水表口径
 */
@Entity(nameInDb = "MR_CALIBER")
public class Caliber {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;                      //ID
    @NotNull
    private int  CALIBER_ID;              //口径id
    @NotNull
    private String CALIBER_NAME;          //口径名称
    @NotNull
    private int CALIBER_VALUE;            //量程值
    @NotNull
    private int CALIBER_INSPECTION_CYCLE; //强检周期
    @NotNull
    private int CALIBER_CHANGE_CYCLE;     //换表周期
    @NotNull
    private double CALIBER_LOW_VOLUME;    //量低系数
    @NotNull
    private double CALIBER_HIGH_VOLUME;   //量高系数
    private String CALIBER_REMARK;        //量程备注
    @Generated(hash = 1623648210)
    public Caliber(Long ID, int CALIBER_ID, @NotNull String CALIBER_NAME,
            int CALIBER_VALUE, int CALIBER_INSPECTION_CYCLE,
            int CALIBER_CHANGE_CYCLE, double CALIBER_LOW_VOLUME,
            double CALIBER_HIGH_VOLUME, String CALIBER_REMARK) {
        this.ID = ID;
        this.CALIBER_ID = CALIBER_ID;
        this.CALIBER_NAME = CALIBER_NAME;
        this.CALIBER_VALUE = CALIBER_VALUE;
        this.CALIBER_INSPECTION_CYCLE = CALIBER_INSPECTION_CYCLE;
        this.CALIBER_CHANGE_CYCLE = CALIBER_CHANGE_CYCLE;
        this.CALIBER_LOW_VOLUME = CALIBER_LOW_VOLUME;
        this.CALIBER_HIGH_VOLUME = CALIBER_HIGH_VOLUME;
        this.CALIBER_REMARK = CALIBER_REMARK;
    }
    @Generated(hash = 602380144)
    public Caliber() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public int getCALIBER_ID() {
        return this.CALIBER_ID;
    }
    public void setCALIBER_ID(int CALIBER_ID) {
        this.CALIBER_ID = CALIBER_ID;
    }
    public String getCALIBER_NAME() {
        return this.CALIBER_NAME;
    }
    public void setCALIBER_NAME(String CALIBER_NAME) {
        this.CALIBER_NAME = CALIBER_NAME;
    }
    public int getCALIBER_VALUE() {
        return this.CALIBER_VALUE;
    }
    public void setCALIBER_VALUE(int CALIBER_VALUE) {
        this.CALIBER_VALUE = CALIBER_VALUE;
    }
    public int getCALIBER_INSPECTION_CYCLE() {
        return this.CALIBER_INSPECTION_CYCLE;
    }
    public void setCALIBER_INSPECTION_CYCLE(int CALIBER_INSPECTION_CYCLE) {
        this.CALIBER_INSPECTION_CYCLE = CALIBER_INSPECTION_CYCLE;
    }
    public int getCALIBER_CHANGE_CYCLE() {
        return this.CALIBER_CHANGE_CYCLE;
    }
    public void setCALIBER_CHANGE_CYCLE(int CALIBER_CHANGE_CYCLE) {
        this.CALIBER_CHANGE_CYCLE = CALIBER_CHANGE_CYCLE;
    }
    public double getCALIBER_LOW_VOLUME() {
        return this.CALIBER_LOW_VOLUME;
    }
    public void setCALIBER_LOW_VOLUME(double CALIBER_LOW_VOLUME) {
        this.CALIBER_LOW_VOLUME = CALIBER_LOW_VOLUME;
    }
    public double getCALIBER_HIGH_VOLUME() {
        return this.CALIBER_HIGH_VOLUME;
    }
    public void setCALIBER_HIGH_VOLUME(double CALIBER_HIGH_VOLUME) {
        this.CALIBER_HIGH_VOLUME = CALIBER_HIGH_VOLUME;
    }
    public String getCALIBER_REMARK() {
        return this.CALIBER_REMARK;
    }
    public void setCALIBER_REMARK(String CALIBER_REMARK) {
        this.CALIBER_REMARK = CALIBER_REMARK;
    }
}
