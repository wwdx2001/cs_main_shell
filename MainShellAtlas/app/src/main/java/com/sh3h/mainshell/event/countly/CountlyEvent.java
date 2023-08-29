package com.sh3h.mainshell.event.countly;

/**
 * Created by dengzhimin on 2016/6/13.
 */
public class CountlyEvent {

    /**  普通点击事件 **/
    public final static String CLICK = "click";

    /**  页面返回按钮事件 **/
    public final static String BACK = "back";

    /** 登录事件  **/
    public final static String LOGIN = "login";

    /** 下载 **/
    public final static String DOWNLOAD = "download";

    /** 操作 **/
    public static class Operate{

        /** 默认操作 **/
        public final static String DEFAULT = "default";

        /** 页面跳转 **/
        public final static String INTENT = "intent";

        /** 弹出dialog **/
        public final static String DIALOG = "dialog";

        /** 改变switch **/
        public final static String SWITCH = "switch";
    }

}
