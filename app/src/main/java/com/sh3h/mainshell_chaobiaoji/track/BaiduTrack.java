package com.sh3h.mainshell_chaobiaoji.track;


import android.content.Context;
import android.util.Log;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.track.ClearCacheTrackRequest;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;
import com.sh3h.mainshell_chaobiaoji.util.DeviceUtil;

public class BaiduTrack {
    private static final String TAG = "BaiduTrack";
    // 定位周期(单位:秒)
    private static final int GATHER_INTERVAL = 5;
    // 打包回传周期(单位:秒)
    private static final int PACK_INTERVAL = 10;

    private Context mContext = null;

    /**
     * 轨迹客户端
     */
    private LBSTraceClient mClient = null;

    /**
     * 轨迹服务
     */
    private Trace mTrace = null;

    /**
     * 轨迹服务ID
     */
    private long serviceId = 150982;

    /**
     * Entity标识
     */
    private String entityName = "myTrace";

    public BaiduTrack(Context context) {
        mContext = context;
        entityName = DeviceUtil.getDeviceID(context);
    }

    public void init() {
        mClient = new LBSTraceClient(mContext);
        mTrace = new Trace(serviceId, entityName);
        //mTrace.setNotification(notification);

        // 设置定位和打包周期
        mClient.setInterval(GATHER_INTERVAL, PACK_INTERVAL);

        // 开启服务
        mClient.startTrace(mTrace, mTraceListener);

        // 开启采集
        mClient.startGather(mTraceListener);
    }

    public void destroy() {
        if (mClient != null) {
            // 停止采集
            mClient.stopGather(mTraceListener);

            // 停止服务
            mClient.stopTrace(mTrace, mTraceListener);
        }
    }

    public void clearCacheTrack() {
        if (mClient != null) {
            ClearCacheTrackRequest request = new ClearCacheTrackRequest();
            mClient.clearCacheTrack(request, null);
        }
    }

    // 初始化轨迹服务监听器
    private OnTraceListener mTraceListener = new OnTraceListener() {
        @Override
        public void onBindServiceCallback(int errorNo, String message) {
            Log.i(TAG, String.format("onBindServiceCallback, status:%d, message:%s ", errorNo, message));
        }

        // 开启服务回调
        @Override
        public void onStartTraceCallback(int status, String message) {
            Log.i(TAG, String.format("onStartTraceCallback, status:%d, message:%s ", status, message));
        }

        // 停止服务回调
        @Override
        public void onStopTraceCallback(int status, String message) {
            Log.i(TAG, String.format("onStopTraceCallback, status:%d, message:%s ", status, message));
        }

        // 开启采集回调
        @Override
        public void onStartGatherCallback(int status, String message) {
            Log.i(TAG, String.format("onStartGatherCallback, status:%d, message:%s ", status, message));
        }

        // 停止采集回调
        @Override
        public void onStopGatherCallback(int status, String message) {
            Log.i(TAG, String.format("onStopGatherCallback, status:%d, message:%s ", status, message));
        }

        // 推送回调
        @Override
        public void onPushCallback(byte messageNo, PushMessage message) {
            Log.i(TAG, "onPushCallback");
        }

        @Override
        public void onInitBOSCallback(int errorNo, String message) {
            Log.i(TAG, String.format("onInitBOSCallback, status:%d, message:%s ", errorNo, message));
        }
    };
}
