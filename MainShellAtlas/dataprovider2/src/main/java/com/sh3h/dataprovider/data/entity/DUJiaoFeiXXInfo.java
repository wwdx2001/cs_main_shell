package com.sh3h.dataprovider.data.entity;


public class DUJiaoFeiXXInfo{
    public enum FilterType {
        ALL,
        ONE
    }

    private boolean isLocal;
    private FilterType filterType;
    private String customerId;
    private String volume;
    private int month;

    public DUJiaoFeiXXInfo() {
        filterType = FilterType.ALL;
        customerId = null;
        volume = null;
        month = 1;
    }

    public DUJiaoFeiXXInfo(String volume, int month) {
        this();
        this.volume = volume;
        this.month = month;
    }

    public DUJiaoFeiXXInfo(String volume, String customerId) {
        this();
        this.filterType = FilterType.ONE;
        this.volume = volume;
        this.customerId = customerId;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
