package com.sh3h.dataprovider.data.local.config;


public class SystemConfig extends BaseConfig {
    /**
     * 数据服务地址
     */
    public static final String PARAM_DATA_SERVER_URL = "data.server.url";

    /**
     * 数据服务地址(备用)
     */
    public static final String PARAM_DATA_SERVER_RESERVED_URL = "data.server.reserved.url";

    /**
     * 文件服务地址
     */
    public static final String PARAM_FILE_SERVER_URL = "file.server.url";

    /**
     * 文件服务地址(备用)
     */
    public static final String PARAM_FILE_SERVER_RESERVED_URL = "file.server.reserved.url";

    /**
     * 是否使用备用地址
     */
    public static final String PARAM_SERVER_USING_RESERVED = "server.using.reserved";

    /**
     * 调试模式
     */
    public static final String PARAM_SYS_DEBUG_MODE = "sys.debug.mode";

    /**
     * 系统区域
     */
    public static final String PARAM_SYS_REGION = "sys.region";
    public static final String REGION_SHANGHAI = "shanghai";
    public static final String REGION_JIADING = "jiading";
    public static final String REGION_WENZHOU = "wenzhou";
    public static final String REGION_SUZHOU = "suzhou";
    public static final String REGION_CHANGSHU = "changshu";
    public static final String REGION_YIWU = "yiwu";

    public SystemConfig() {

    }

    public void loadDefaultValues() {
        set(PARAM_DATA_SERVER_URL, "");
        set(PARAM_DATA_SERVER_RESERVED_URL, "");
        set(PARAM_FILE_SERVER_URL, "");
        set(PARAM_FILE_SERVER_RESERVED_URL, "");
        set(PARAM_SERVER_USING_RESERVED, false);
        set(PARAM_SYS_DEBUG_MODE, true);
        setRead(true);
    }

}
