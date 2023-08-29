package com.sh3h.mainshell_chaobiaoji.ui.huawei;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sh3h.mainshell_chaobiaoji.AppChannel;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mainshell_chaobiaoji.ui.welcome.WelcomeActivity;
import com.sh3h.mobileutil.util.LogUtil;
import com.umeng.message.UmengNotifyClickActivity;
import com.umeng.message.common.UmLog;

import org.android.agoo.common.AgooConstants;

public class HWPushActivity extends UmengNotifyClickActivity {
    private static String TAG = HWPushActivity.class.getName();
    private Handler handler;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String appChannel = getAppChannel();
        if (AppChannel.GAS_GROUP_CHANNEL.equalsIgnoreCase(appChannel)) {
            setContentView(R.layout.activity_hwpush_green);
        } else {
            setContentView(R.layout.activity_hwpush);
        }
        handler = new MyHandler(this);
        handler.sendMessage(Message.obtain());
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        UmLog.i(TAG, body);
//        Message message = Message.obtain();
//        message.obj = body;
//        handler.sendMessage(message);
        Log.i(TAG, "onMessage");
    }

    private static class MyHandler extends Handler {
        private Activity activity;

        public MyHandler(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "<<<---HWPushActivity--->>>");
            Intent intent = new Intent(activity, WelcomeActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    private String getAppChannel() {
        try {
            ApplicationInfo appInfo =
                    getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
            return null;
        }
    }
}
