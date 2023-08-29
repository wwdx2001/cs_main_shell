package com.sh3h.dataprovider.data.entity;


import java.util.List;

public class DUCardInfo extends DUBaseInfo {
    /**
     * ALL_NO_CONDITION 无条件获取所有本地表卡信息
     */
    public enum FilterType {
        NONE,
        VOLUME_ALL,
        GROUPID_ALL,
        SEARCHING_ONE,
        UPDATING_ALL,
        UPLOADING_ALL,
        ALL_NO_CONDITION,
        FORMAL,
        TEMPORARY,
        SAMPLING
    }

    private FilterType filterType;
    private String account;
    private int taskId;
    private String volume;
    private String customerId;
    private int groupId;
    private List<DUCard> duCardList;

    public DUCardInfo() {
        filterType = FilterType.NONE;
        account = null;
        taskId = 0;
        volume = null;
        customerId = null;
        duCardList = null;
    }

    public DUCardInfo(FilterType filterType, String account, int taskId, String volume) {
        this.filterType = filterType;
        this.account = account;
        this.taskId = taskId;
        this.volume = volume;
    }

    public DUCardInfo(String volume, FilterType filterType) {
        this.volume = volume;
        this.filterType = filterType;
    }

    public DUCardInfo(FilterType filterType,
                      String volume,
                      String customerId) {
        this.filterType = filterType;
        this.volume = volume;
        this.customerId = customerId;
    }


    public DUCardInfo(FilterType filterType, String account, int taskId, String volume, List<DUCard> duCardList) {
        this.filterType = filterType;
        this.account = account;
        this.taskId = taskId;
        this.volume = volume;
        this.duCardList = duCardList;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public List<DUCard> getDuCardList() {
        return duCardList;
    }

    public void setDuCardList(List<DUCard> duCardList) {
        this.duCardList = duCardList;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
