package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * 价格信息
 */
@Entity(nameInDb = "MR_PRICE")
public class Price {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;              //ID
    @NotNull
    private int PRICE_ID;         //价格id
    @NotNull
    private int PRICE_CODE;       //用水性质编码
    @NotNull
    private String PRICE_NAME;    //用水性质名字
    @NotNull
    private int  PRICE_LIST_ID;   //调价号
    @NotNull
    private int PRICE_CATEGORY;   //价格分类
    private String REMARK;        //备注
    private int  PRICE_ADJUSTED;  //价格是否调整过
    @Generated(hash = 58564400)
    public Price(Long ID, int PRICE_ID, int PRICE_CODE, @NotNull String PRICE_NAME,
            int PRICE_LIST_ID, int PRICE_CATEGORY, String REMARK, int PRICE_ADJUSTED) {
        this.ID = ID;
        this.PRICE_ID = PRICE_ID;
        this.PRICE_CODE = PRICE_CODE;
        this.PRICE_NAME = PRICE_NAME;
        this.PRICE_LIST_ID = PRICE_LIST_ID;
        this.PRICE_CATEGORY = PRICE_CATEGORY;
        this.REMARK = REMARK;
        this.PRICE_ADJUSTED = PRICE_ADJUSTED;
    }
    @Generated(hash = 812905808)
    public Price() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public int getPRICE_ID() {
        return this.PRICE_ID;
    }
    public void setPRICE_ID(int PRICE_ID) {
        this.PRICE_ID = PRICE_ID;
    }
    public int getPRICE_CODE() {
        return this.PRICE_CODE;
    }
    public void setPRICE_CODE(int PRICE_CODE) {
        this.PRICE_CODE = PRICE_CODE;
    }
    public String getPRICE_NAME() {
        return this.PRICE_NAME;
    }
    public void setPRICE_NAME(String PRICE_NAME) {
        this.PRICE_NAME = PRICE_NAME;
    }
    public int getPRICE_LIST_ID() {
        return this.PRICE_LIST_ID;
    }
    public void setPRICE_LIST_ID(int PRICE_LIST_ID) {
        this.PRICE_LIST_ID = PRICE_LIST_ID;
    }
    public int getPRICE_CATEGORY() {
        return this.PRICE_CATEGORY;
    }
    public void setPRICE_CATEGORY(int PRICE_CATEGORY) {
        this.PRICE_CATEGORY = PRICE_CATEGORY;
    }
    public String getREMARK() {
        return this.REMARK;
    }
    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
    public int getPRICE_ADJUSTED() {
        return this.PRICE_ADJUSTED;
    }
    public void setPRICE_ADJUSTED(int PRICE_ADJUSTED) {
        this.PRICE_ADJUSTED = PRICE_ADJUSTED;
    }

}
