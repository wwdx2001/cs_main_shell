package com.sh3h.dataprovider.data.remote;

import android.content.Context;


import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.config.SystemConfig;
import com.squareup.otto.Bus;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class HttpHelper {
    private static final String TAG = "HttpHelper";
    private Context mContext;
    private final ConfigHelper mConfigHelper;
    private Bus mBus;
    private boolean isConnected;
    private RestfulApiService restfulApiService;

    @Inject
    public HttpHelper(ConfigHelper configHelper) {
        mConfigHelper = configHelper;
        isConnected = false;
    }

    public void init(Context context, Bus bus) {
        mContext = context;
        mBus = bus;
    }

    private void connect() {
        if (isConnected) {
            return;
        }

        SystemConfig systemConfig = mConfigHelper.getSystemConfig();
        String baseUrl;
        if (!systemConfig.getBoolean(SystemConfig.PARAM_SERVER_USING_RESERVED, false)) {
            baseUrl  = systemConfig.getString(SystemConfig.PARAM_DATA_SERVER_URL);
        } else {
            baseUrl = systemConfig.getString(SystemConfig.PARAM_DATA_SERVER_RESERVED_URL);
        }
        restfulApiService = RestfulApiService.Factory.newInstance(mBus, baseUrl);
        isConnected = true;
    }
}
