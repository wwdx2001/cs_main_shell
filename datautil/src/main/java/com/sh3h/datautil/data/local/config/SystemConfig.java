package com.sh3h.datautil.data.local.config;


public class SystemConfig extends BaseConfig {
    /**
     * 数据后台地址
     */
    public static final String PARAM_SERVER_BASE_URI = "server.baseuri";
    /**
     * 数据后台地址(备用)
     */
    public static final String PARAM_RESERVED_SERVER_BASE_URI = "reserved.server.baseuri";
    /**
     * 推送后台地址
     */
    public static final String PARAM_BROKER_URL = "broker.url";
    /**
     * 推送后台地址
     */
    public static final String PARAM_RESERVED_BROKER_URL = "reserved.broker.url";
    /**
     * 是否选用备用地址
     */
    public static final String PARAM_USING_RESERVED_ADDRESS = "using.reserved.address";
    /**
     * 调试模式
     */
    public static final String PARAM_SYS_IS_DEBUG_MODE = "sys.debug.mode";
    /**
     * 呼吸包发送频率
     */
    public static final String PARAM_KEEP_LIVE_INTERVAL = "sys.keepalive.interval";
    public static final int KEEP_LIVE_INTERVAL_DEFAULT_VALUE = 30000;

    public static final String PARAM_KEEP_LIVE_RESERVED_COUNT = "sys.keepalive.reserved.count";
    public static final int KEEP_LIVE_RESERVED_DEFAULT_COUNT = 10000;

    /**
     * region
     */
    public static final String PARAM_SYS_REGION = "sys.region";
    public static final String REGION_SHANGHAI = "shanghai";
    public static final String REGION_JIADING = "jiading";
    public static final String REGION_WENZHOU = "wenzhou";
    public static final String REGION_SUZHOU = "suzhou";
    public static final String REGION_CHANGSHU = "changshu";
    public static final String REGION_YIWU = "yiwu";
    public static final String REGION_CHUANSHA = "chuansha";
    public static final String REGION_GASGROUP = "gasgroup";
    public static final String REGION_DAZHONG = "dazhong";

    /**
     * update version automatically
     */
    public static final String PARAM_SYS_UPDATING_VERSION_AUTO = "sys.updating.version.auto";

    /**
     * 监控类型：none & countly & umeng
     */
    public static final String PARAM_SYS_MONITOR_TYPE = "sys.monitor.type";

    public enum MonitorType {
        NONE(0, "none"),
        COUTLY(1, "countly"),
        UMENG(2, "umeng"),
        BUGLY(3, "bugly");

        private MonitorType(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public static MonitorType toMonitorType(String name) {
            switch (name) {
                case "none":
                    return NONE;
                case "countly":
                    return COUTLY;
                case "umeng":
                    return UMENG;
                case "bugly":
                    return BUGLY;
                default:
                    return NONE;
            }
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private int index;
        private String name;
    }

    /**
     * Countly服务器地址
     **/
    public static final String PARAM_COUNTLY_SERVER_URI = "countly.server.uri";

    /**
     * Countly默认服务器地址
     **/
    public static final String DEFAULT_COUNTLY_SERVER_URI = "http://128.1.2.164";

    /**
     * gps定位方式
     */
    public static final String PARAM_SYS_GPS_TYPE = "sys.gps.type";
    public static final String GPS_TYPE_NONE = "none";
    public static final String GPS_TYPE_SYSTEM = "system";
    public static final String GPS_TYPE_BAIDU = "baidu";

    public static final String PARAM_USING_ROLE_FUNCTION = "using.role.function";

    /**
     * message push
     */
    public static final String PARAM_SYS_PUSH_TYPE = "sys.push.type";
    public static final String SYS_PUSH_TYPE_MQTT = "mqtt";
    public static final String SYS_PUSH_TYPE_UMENG = "umeng";

    public static final String PARAM_LOCATION_URL = "location.url";
    public static final String LOCATION_BAIDU = "baidu";

    public static final String SYS_BAIDU_TRACK = "sys.baidu.track";

    public static final String SYS_LOCATION_SCAN_SPAN = "sys.location.scan.span";
    public static final int LOCATION_SCAN_SPAN_DEFAULT = 1000;

    public SystemConfig() {

    }

}
