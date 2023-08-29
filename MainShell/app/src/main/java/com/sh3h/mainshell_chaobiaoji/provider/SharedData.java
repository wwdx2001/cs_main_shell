package com.sh3h.mainshell_chaobiaoji.provider;


import android.net.Uri;

public class SharedData {
    /*Data Field*/
    public static final String SD_KEY = "sd_key";
    public static final String SD_VALUE = "sd_value";

    /*Default sort order*/
    public static final String DEFAULT_SORT_ORDER = SD_KEY + " ASC";

    /*Call Method*/
    //public static final String METHOD_GET_ITEM_COUNT = "METHOD_GET_ITEM_COUNT";
    //public static final String KEY_ITEM_COUNT = "KEY_ITEM_COUNT";

    /*Authority*/
    public static final String AUTHORITY = "com.sh3h.mainshell_chaobiaoji.shared.data.provider";

    /*Match Code*/
    public static final int ITEM = 1;
    public static final int ITEM_KEY = 2;
    //public static final int ITEM_POS = 3;

    /*MIME*/
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.sh3h.provider.shared.data";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.sh3h.provider.shared.data";

    /*Content URI*/
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/item");
    //public static final Uri CONTENT_POS_URI = Uri.parse("content://" + AUTHORITY + "/pos");
}
