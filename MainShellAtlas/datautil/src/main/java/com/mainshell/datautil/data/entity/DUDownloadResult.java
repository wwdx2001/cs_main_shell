package com.mainshell.datautil.data.entity;


public class DUDownloadResult extends DUResponse {
    public static final String TEMPORARY_VOLUME = "temporary_volume";

    public enum FilterType {
        NONE("none"),
        CARD("card"),
        RECORD("record"),
        PRERECORD("prerecord"),
        PAYMENT("payment"),
        ARREARAGE("arrearage"),
        REPLACEMENT("replacement"),
        TEMPORARY("temporary"),
        WAIFU("waifu");

        private String name;
        FilterType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private FilterType filterType;
    private String volume;
    private int count;

    public DUDownloadResult() {
        filterType = FilterType.NONE;
        volume = null;
        count = 0;
    }

    public DUDownloadResult(FilterType filterType,
                            String volume,
                            int count) {
        this.filterType = filterType;
        this.volume = volume;
        this.count = count;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
