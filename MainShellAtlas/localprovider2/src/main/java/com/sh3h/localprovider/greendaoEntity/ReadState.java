package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 抄表状态
 */
@Entity(nameInDb = "MR_READSTATE")
public class ReadState {
    @Id
    private Long STATE_ID;          //抄表状态id
    @NotNull
    private String STATE_CODE;      //抄表算法编码
    @NotNull
    private String STATE_NAME;      //状态名称
    @NotNull
    private int  STATE_TYPE;        //状态类型
    @NotNull
    private String STATE_PARENT_ID; //父类id
    @NotNull
    private String ALGORITHM_CODE;  //抄表算法编码
    @Generated(hash = 1022599933)
    public ReadState(Long STATE_ID, @NotNull String STATE_CODE,
            @NotNull String STATE_NAME, int STATE_TYPE,
            @NotNull String STATE_PARENT_ID, @NotNull String ALGORITHM_CODE) {
        this.STATE_ID = STATE_ID;
        this.STATE_CODE = STATE_CODE;
        this.STATE_NAME = STATE_NAME;
        this.STATE_TYPE = STATE_TYPE;
        this.STATE_PARENT_ID = STATE_PARENT_ID;
        this.ALGORITHM_CODE = ALGORITHM_CODE;
    }
    @Generated(hash = 802390038)
    public ReadState() {
    }
    public Long getSTATE_ID() {
        return this.STATE_ID;
    }
    public void setSTATE_ID(Long STATE_ID) {
        this.STATE_ID = STATE_ID;
    }
    public String getSTATE_CODE() {
        return this.STATE_CODE;
    }
    public void setSTATE_CODE(String STATE_CODE) {
        this.STATE_CODE = STATE_CODE;
    }
    public String getSTATE_NAME() {
        return this.STATE_NAME;
    }
    public void setSTATE_NAME(String STATE_NAME) {
        this.STATE_NAME = STATE_NAME;
    }
    public int getSTATE_TYPE() {
        return this.STATE_TYPE;
    }
    public void setSTATE_TYPE(int STATE_TYPE) {
        this.STATE_TYPE = STATE_TYPE;
    }
    public String getSTATE_PARENT_ID() {
        return this.STATE_PARENT_ID;
    }
    public void setSTATE_PARENT_ID(String STATE_PARENT_ID) {
        this.STATE_PARENT_ID = STATE_PARENT_ID;
    }
    public String getALGORITHM_CODE() {
        return this.ALGORITHM_CODE;
    }
    public void setALGORITHM_CODE(String ALGORITHM_CODE) {
        this.ALGORITHM_CODE = ALGORITHM_CODE;
    }
}
