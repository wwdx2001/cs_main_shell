package com.sh3h.localprovider.greendaoEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * 词语信息
 */
@Entity(nameInDb = "MR_WORD")
public class Word {
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long ID;                 //ID
    @NotNull
    private int  WORD_ID;            //词语id
    @NotNull
    private int  WORD_PARENT_ID;     //父级词语id
    @NotNull
    private String  WORD_GROUP_KEY;  //词语组key
    @NotNull
    private String  WORD_KEY;        //词语的key
    @NotNull
    private String  WORD_TEXT;       //词语文本
    @NotNull
    private String  WORD_VALUE;      //词语值
    private String  WORD_REMARK;     //词语备注
    @Generated(hash = 186183221)
    public Word(Long ID, int WORD_ID, int WORD_PARENT_ID, @NotNull String WORD_GROUP_KEY,
            @NotNull String WORD_KEY, @NotNull String WORD_TEXT, @NotNull String WORD_VALUE,
            String WORD_REMARK) {
        this.ID = ID;
        this.WORD_ID = WORD_ID;
        this.WORD_PARENT_ID = WORD_PARENT_ID;
        this.WORD_GROUP_KEY = WORD_GROUP_KEY;
        this.WORD_KEY = WORD_KEY;
        this.WORD_TEXT = WORD_TEXT;
        this.WORD_VALUE = WORD_VALUE;
        this.WORD_REMARK = WORD_REMARK;
    }
    @Generated(hash = 3342184)
    public Word() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public int getWORD_ID() {
        return this.WORD_ID;
    }
    public void setWORD_ID(int WORD_ID) {
        this.WORD_ID = WORD_ID;
    }
    public int getWORD_PARENT_ID() {
        return this.WORD_PARENT_ID;
    }
    public void setWORD_PARENT_ID(int WORD_PARENT_ID) {
        this.WORD_PARENT_ID = WORD_PARENT_ID;
    }
    public String getWORD_GROUP_KEY() {
        return this.WORD_GROUP_KEY;
    }
    public void setWORD_GROUP_KEY(String WORD_GROUP_KEY) {
        this.WORD_GROUP_KEY = WORD_GROUP_KEY;
    }
    public String getWORD_KEY() {
        return this.WORD_KEY;
    }
    public void setWORD_KEY(String WORD_KEY) {
        this.WORD_KEY = WORD_KEY;
    }
    public String getWORD_TEXT() {
        return this.WORD_TEXT;
    }
    public void setWORD_TEXT(String WORD_TEXT) {
        this.WORD_TEXT = WORD_TEXT;
    }
    public String getWORD_VALUE() {
        return this.WORD_VALUE;
    }
    public void setWORD_VALUE(String WORD_VALUE) {
        this.WORD_VALUE = WORD_VALUE;
    }
    public String getWORD_REMARK() {
        return this.WORD_REMARK;
    }
    public void setWORD_REMARK(String WORD_REMARK) {
        this.WORD_REMARK = WORD_REMARK;
    }
}
