package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 水表量程
 */
@Entity(nameInDb = "MR_CALIBERRANGE")
public class CaliberRange {
    @Id
    private Long RANGE_ID;      //量程主键
    @NotNull
    private Long RANGE_NAME;    //量程名称
    @NotNull
    private Long RANGE_VALUE;   //量程值
    @NotNull
    private Long RANGE_VOLUME;  //量程系数
    @Generated(hash = 291872910)
    public CaliberRange(Long RANGE_ID, @NotNull Long RANGE_NAME,
            @NotNull Long RANGE_VALUE, @NotNull Long RANGE_VOLUME) {
        this.RANGE_ID = RANGE_ID;
        this.RANGE_NAME = RANGE_NAME;
        this.RANGE_VALUE = RANGE_VALUE;
        this.RANGE_VOLUME = RANGE_VOLUME;
    }
    @Generated(hash = 1261436081)
    public CaliberRange() {
    }
    public Long getRANGE_ID() {
        return this.RANGE_ID;
    }
    public void setRANGE_ID(Long RANGE_ID) {
        this.RANGE_ID = RANGE_ID;
    }
    public Long getRANGE_NAME() {
        return this.RANGE_NAME;
    }
    public void setRANGE_NAME(Long RANGE_NAME) {
        this.RANGE_NAME = RANGE_NAME;
    }
    public Long getRANGE_VALUE() {
        return this.RANGE_VALUE;
    }
    public void setRANGE_VALUE(Long RANGE_VALUE) {
        this.RANGE_VALUE = RANGE_VALUE;
    }
    public Long getRANGE_VOLUME() {
        return this.RANGE_VOLUME;
    }
    public void setRANGE_VOLUME(Long RANGE_VOLUME) {
        this.RANGE_VOLUME = RANGE_VOLUME;
    }
}
