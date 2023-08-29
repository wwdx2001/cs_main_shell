package com.sh3h.mainshell_chaobiaoji.event;


public class UIBusEvent {
    public static class FileStart {
        private String appName;
        private String appId;

        public FileStart(String appName, String appId) {
            this.appName = appName;
            this.appId = appId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }
    }

    public static class FileStep {
        public enum FileType {
            APK,
            CONFIG,
            DATA
        }

        private FileType fileType;
        private String appName;
        private String appId;
        private int percent;

        public FileStep(FileType fileType, String appName, String appId, int percent) {
            this.fileType = fileType;
            this.appName = appName;
            this.appId = appId;
            this.percent = percent;
        }

        public FileType getFileType() {
            return fileType;
        }

        public void setFileType(FileType fileType) {
            this.fileType = fileType;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }
    }

    public static class FileEnd {
        private String appName;
        private String appId;
        private String packageName;
        private String url;
        private boolean isSuccess;
        private String message;
        private boolean isApkEnd;

        public FileEnd(String appName, String appId, String packageName, String url,
                       boolean isSuccess, String message, boolean isApkEnd) {
            this.appName = appName;
            this.appId = appId;
            this.packageName = packageName;
            this.url = url;
            this.isSuccess = isSuccess;
            this.message = message;
            this.isApkEnd = isApkEnd;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public boolean isApkEnd() {
            return isApkEnd;
        }

        public void setApkEnd(boolean apkEnd) {
            isApkEnd = apkEnd;
        }
    }

    public static class ApkStatus {
        private String appId;
        private String packageName;
        private String action;

        public ApkStatus(String appId, String packageName, String action) {
            this.appId = appId;
            this.packageName = packageName;
            this.action = action;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }

    public static class ExitingSystem {
        public ExitingSystem() {
        }
    }

    public static class GridStyle {

    }

    public static class FileServiceReady {

    }

    public static class UpdatingComponent {
        private String packageName;
        private String activity;
        private int count;

        public UpdatingComponent(String packageName, String activity, int count) {
            this.packageName = packageName;
            this.activity = activity;
            this.count = count;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
