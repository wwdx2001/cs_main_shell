package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * 价格详细信息
 */
@Entity(nameInDb = "MR_PRICEDETAIL")
public class PriceDetail {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;              //ID
    @NotNull
    private int  PRICE_LIST_ID;   //调价号
    @NotNull
    private int  PRICE_CODE;      //用水性质编码
    @NotNull
    private int  PILD;            //费用组成id
    @NotNull
    private int  LEVEL_TYPE;      //阶梯类型
    @NotNull
    private int  LEVEL_INDEX;     //阶梯级别
    @NotNull
    private int  START_WATE;      //起始水量
    @NotNull
    private int  END_WATER;       //结束水量
    @NotNull
    private double PRICE;         //单价
    @NotNull
    private int  CACULATE_MODE;   //计费模式
    @NotNull
    private double WATER_NUMBER;  //水量系数
    private String REMARK;        //备注
    @Generated(hash = 1240349488)
    public PriceDetail(Long ID, int PRICE_LIST_ID, int PRICE_CODE, int PILD,
            int LEVEL_TYPE, int LEVEL_INDEX, int START_WATE, int END_WATER,
            double PRICE, int CACULATE_MODE, double WATER_NUMBER, String REMARK) {
        this.ID = ID;
        this.PRICE_LIST_ID = PRICE_LIST_ID;
        this.PRICE_CODE = PRICE_CODE;
        this.PILD = PILD;
        this.LEVEL_TYPE = LEVEL_TYPE;
        this.LEVEL_INDEX = LEVEL_INDEX;
        this.START_WATE = START_WATE;
        this.END_WATER = END_WATER;
        this.PRICE = PRICE;
        this.CACULATE_MODE = CACULATE_MODE;
        this.WATER_NUMBER = WATER_NUMBER;
        this.REMARK = REMARK;
    }
    @Generated(hash = 1864894116)
    public PriceDetail() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public int getPRICE_LIST_ID() {
        return this.PRICE_LIST_ID;
    }
    public void setPRICE_LIST_ID(int PRICE_LIST_ID) {
        this.PRICE_LIST_ID = PRICE_LIST_ID;
    }
    public int getPRICE_CODE() {
        return this.PRICE_CODE;
    }
    public void setPRICE_CODE(int PRICE_CODE) {
        this.PRICE_CODE = PRICE_CODE;
    }
    public int getPILD() {
        return this.PILD;
    }
    public void setPILD(int PILD) {
        this.PILD = PILD;
    }
    public int getLEVEL_TYPE() {
        return this.LEVEL_TYPE;
    }
    public void setLEVEL_TYPE(int LEVEL_TYPE) {
        this.LEVEL_TYPE = LEVEL_TYPE;
    }
    public int getLEVEL_INDEX() {
        return this.LEVEL_INDEX;
    }
    public void setLEVEL_INDEX(int LEVEL_INDEX) {
        this.LEVEL_INDEX = LEVEL_INDEX;
    }
    public int getSTART_WATE() {
        return this.START_WATE;
    }
    public void setSTART_WATE(int START_WATE) {
        this.START_WATE = START_WATE;
    }
    public int getEND_WATER() {
        return this.END_WATER;
    }
    public void setEND_WATER(int END_WATER) {
        this.END_WATER = END_WATER;
    }
    public double getPRICE() {
        return this.PRICE;
    }
    public void setPRICE(double PRICE) {
        this.PRICE = PRICE;
    }
    public int getCACULATE_MODE() {
        return this.CACULATE_MODE;
    }
    public void setCACULATE_MODE(int CACULATE_MODE) {
        this.CACULATE_MODE = CACULATE_MODE;
    }
    public double getWATER_NUMBER() {
        return this.WATER_NUMBER;
    }
    public void setWATER_NUMBER(double WATER_NUMBER) {
        this.WATER_NUMBER = WATER_NUMBER;
    }
    public String getREMARK() {
        return this.REMARK;
    }
    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
