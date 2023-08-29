package com.sh3h.localprovider;


import android.content.Context;

public interface IDataProvider {
    /**
     * initialize the db
     * @param path
     * @param context
     * @return
     */
    boolean init(String path, Context context);

    /**
     * release the db
     */
    void destroy();
}
