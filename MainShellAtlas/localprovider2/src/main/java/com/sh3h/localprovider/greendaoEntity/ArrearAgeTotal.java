package com.sh3h.localprovider.greendaoEntity;

/**
 * Created by zhangjing on 2017/4/13.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 欠费统计
 */
@Entity(nameInDb = "MR_ARREARAGETOTAL")
public class ArrearAgeTotal {

    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;            //ID

    @NotNull
    private int CHARGE_COUNT;//总笔数

    @NotNull
    private double CHARGE_MONEY;//总金额

    @NotNull
    private double WATER_FEE;//水费

    @NotNull
    private double LATE_FEE;//违约金

    @Generated(hash = 735763980)
    public ArrearAgeTotal(Long ID, int CHARGE_COUNT, double CHARGE_MONEY,
            double WATER_FEE, double LATE_FEE) {
        this.ID = ID;
        this.CHARGE_COUNT = CHARGE_COUNT;
        this.CHARGE_MONEY = CHARGE_MONEY;
        this.WATER_FEE = WATER_FEE;
        this.LATE_FEE = LATE_FEE;
    }

    @Generated(hash = 992159063)
    public ArrearAgeTotal() {
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public int getCHARGE_COUNT() {
        return this.CHARGE_COUNT;
    }

    public void setCHARGE_COUNT(int CHARGE_COUNT) {
        this.CHARGE_COUNT = CHARGE_COUNT;
    }

    public double getCHARGE_MONEY() {
        return this.CHARGE_MONEY;
    }

    public void setCHARGE_MONEY(double CHARGE_MONEY) {
        this.CHARGE_MONEY = CHARGE_MONEY;
    }

    public double getWATER_FEE() {
        return this.WATER_FEE;
    }

    public void setWATER_FEE(double WATER_FEE) {
        this.WATER_FEE = WATER_FEE;
    }

    public double getLATE_FEE() {
        return this.LATE_FEE;
    }

    public void setLATE_FEE(double LATE_FEE) {
        this.LATE_FEE = LATE_FEE;
    }

}
