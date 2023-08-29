package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 水表厂家
 */
@Entity(nameInDb = "MR_METERPRODUCER")
public class MeterProducer {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;                      //ID
    @NotNull
    private String PRODUCER_ID;            //水表厂家编号
    @NotNull
    private int  PRODUCER_NAME;             //水表厂家名称
    private double PRODUCER_REMARK;       //水表厂家备注
    @Generated(hash = 1538630101)
    public MeterProducer(Long ID, @NotNull String PRODUCER_ID, int PRODUCER_NAME,
            double PRODUCER_REMARK) {
        this.ID = ID;
        this.PRODUCER_ID = PRODUCER_ID;
        this.PRODUCER_NAME = PRODUCER_NAME;
        this.PRODUCER_REMARK = PRODUCER_REMARK;
    }
    @Generated(hash = 2038680545)
    public MeterProducer() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getPRODUCER_ID() {
        return this.PRODUCER_ID;
    }
    public void setPRODUCER_ID(String PRODUCER_ID) {
        this.PRODUCER_ID = PRODUCER_ID;
    }
    public int getPRODUCER_NAME() {
        return this.PRODUCER_NAME;
    }
    public void setPRODUCER_NAME(int PRODUCER_NAME) {
        this.PRODUCER_NAME = PRODUCER_NAME;
    }
    public double getPRODUCER_REMARK() {
        return this.PRODUCER_REMARK;
    }
    public void setPRODUCER_REMARK(double PRODUCER_REMARK) {
        this.PRODUCER_REMARK = PRODUCER_REMARK;
    }
    }
