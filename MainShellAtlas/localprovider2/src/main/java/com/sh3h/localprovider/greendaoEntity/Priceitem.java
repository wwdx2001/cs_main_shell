package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 价格信息
 */
@Entity(nameInDb = "MR_PRICEITEM")
public class Priceitem {
    @Id
    private Long PRICE_ITEM_ID;  //费用组成Id
    @NotNull
    private String PRICE_ITEM_NAME;//费用组成名称
    @NotNull
    private int PRICE_ITEM_CATEGORY;//费用类型

    private String REMARK;//备注

    @Generated(hash = 786388077)
    public Priceitem(Long PRICE_ITEM_ID, @NotNull String PRICE_ITEM_NAME,
            int PRICE_ITEM_CATEGORY, String REMARK) {
        this.PRICE_ITEM_ID = PRICE_ITEM_ID;
        this.PRICE_ITEM_NAME = PRICE_ITEM_NAME;
        this.PRICE_ITEM_CATEGORY = PRICE_ITEM_CATEGORY;
        this.REMARK = REMARK;
    }

    @Generated(hash = 1746524338)
    public Priceitem() {
    }

    public Long getPRICE_ITEM_ID() {
        return this.PRICE_ITEM_ID;
    }

    public void setPRICE_ITEM_ID(Long PRICE_ITEM_ID) {
        this.PRICE_ITEM_ID = PRICE_ITEM_ID;
    }

    public String getPRICE_ITEM_NAME() {
        return this.PRICE_ITEM_NAME;
    }

    public void setPRICE_ITEM_NAME(String PRICE_ITEM_NAME) {
        this.PRICE_ITEM_NAME = PRICE_ITEM_NAME;
    }

    public int getPRICE_ITEM_CATEGORY() {
        return this.PRICE_ITEM_CATEGORY;
    }

    public void setPRICE_ITEM_CATEGORY(int PRICE_ITEM_CATEGORY) {
        this.PRICE_ITEM_CATEGORY = PRICE_ITEM_CATEGORY;
    }

    public String getREMARK() {
        return this.REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
