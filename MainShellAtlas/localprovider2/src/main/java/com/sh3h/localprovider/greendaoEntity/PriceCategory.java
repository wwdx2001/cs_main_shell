package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 价格分类信息
 */
@Entity(nameInDb = "MR_PRICECATEGORY")
public class PriceCategory {
    @Id
    private Long PRICE_CATEGORY_ID;  //价格分类主键
    @NotNull
    private String PRICE_CATEGORY_CODE; //价格分类编码
    @NotNull
    private String PRICE_CATEGORY_NAME;  //价格分类名称
    @NotNull
    private int PRICE_CATEGORY_PARENT_ID;  //上级节点
    @NotNull
    private int PRICE_CATEGORY_SORT_INDEX; //排序

    @Generated(hash = 67177728)
    public PriceCategory(Long PRICE_CATEGORY_ID,
            @NotNull String PRICE_CATEGORY_CODE,
            @NotNull String PRICE_CATEGORY_NAME, int PRICE_CATEGORY_PARENT_ID,
            int PRICE_CATEGORY_SORT_INDEX) {
        this.PRICE_CATEGORY_ID = PRICE_CATEGORY_ID;
        this.PRICE_CATEGORY_CODE = PRICE_CATEGORY_CODE;
        this.PRICE_CATEGORY_NAME = PRICE_CATEGORY_NAME;
        this.PRICE_CATEGORY_PARENT_ID = PRICE_CATEGORY_PARENT_ID;
        this.PRICE_CATEGORY_SORT_INDEX = PRICE_CATEGORY_SORT_INDEX;
    }

    @Generated(hash = 31830830)
    public PriceCategory() {
    }

    public Long getPRICE_CATEGORY_ID() {
        return this.PRICE_CATEGORY_ID;
    }

    public void setPRICE_CATEGORY_ID(Long PRICE_CATEGORY_ID) {
        this.PRICE_CATEGORY_ID = PRICE_CATEGORY_ID;
    }

    public String getPRICE_CATEGORY_CODE() {
        return this.PRICE_CATEGORY_CODE;
    }

    public void setPRICE_CATEGORY_CODE(String PRICE_CATEGORY_CODE) {
        this.PRICE_CATEGORY_CODE = PRICE_CATEGORY_CODE;
    }

    public String getPRICE_CATEGORY_NAME() {
        return this.PRICE_CATEGORY_NAME;
    }

    public void setPRICE_CATEGORY_NAME(String PRICE_CATEGORY_NAME) {
        this.PRICE_CATEGORY_NAME = PRICE_CATEGORY_NAME;
    }

    public int getPRICE_CATEGORY_PARENT_ID() {
        return this.PRICE_CATEGORY_PARENT_ID;
    }

    public void setPRICE_CATEGORY_PARENT_ID(int PRICE_CATEGORY_PARENT_ID) {
        this.PRICE_CATEGORY_PARENT_ID = PRICE_CATEGORY_PARENT_ID;
    }

    public int getPRICE_CATEGORY_SORT_INDEX() {
        return this.PRICE_CATEGORY_SORT_INDEX;
    }

    public void setPRICE_CATEGORY_SORT_INDEX(int PRICE_CATEGORY_SORT_INDEX) {
        this.PRICE_CATEGORY_SORT_INDEX = PRICE_CATEGORY_SORT_INDEX;
    }
}
