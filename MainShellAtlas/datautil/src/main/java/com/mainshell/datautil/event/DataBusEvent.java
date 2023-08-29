package com.mainshell.datautil.event;


public class DataBusEvent {
    public static class FileResult {
        public enum FilterType {
            APK,
            //CONFIG,
            DATA
        }

        private FilterType filterType;
        private String appId;
        private boolean isSuccess;
        private String message;

        public FileResult(FilterType filterType, String appId, boolean isSuccess, String message) {
            this.filterType = filterType;
            this.appId = appId;
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public FilterType getFilterType() {
            return filterType;
        }

        public void setFilterType(FilterType filterType) {
            this.filterType = filterType;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
