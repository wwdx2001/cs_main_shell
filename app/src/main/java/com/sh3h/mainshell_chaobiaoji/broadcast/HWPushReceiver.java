package com.sh3h.mainshell_chaobiaoji.broadcast;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
//import android.util.Log;

import com.huawei.hms.support.api.push.PushReceiver;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import static android.content.Context.NOTIFICATION_SERVICE;


public class HWPushReceiver extends PushReceiver {
    private static final String TAG = "PushReceiverExample";

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        String content = "get token and belongId successful, token = " + token + ",belongId = " + belongId;
        LogUtil.i(TAG, content);

        Context c = context.getApplicationContext();
        if (c instanceof MainApplication) {
            ((MainApplication)c).setHwPushToken(token);
        }
    }

    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            String content = new String(msg, "UTF-8");
            LogUtil.i(TAG, content);

            Context c = context.getApplicationContext();
            String title = null;
            if (c instanceof MainApplication) {
                ((MainApplication)c).turnOnScreen();
                title = ((MainApplication)c).getResources().getString(R.string.text_important_notification);
            }

            if (TextUtil.isNullOrEmpty(title)) {
                title = "Notification";
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            // 设置通知的基本信息：icon、标题、内容
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setAutoCancel(true);

            // 设置通知的点击行为：这里启动一个 Activity
//            Intent intent = new Intent(context, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(pendingIntent);

            // 发送通知 id 需要在应用内唯一
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(12345, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
        return false;
    }

    public void onEvent(Context context, Event event, Bundle extras) {
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context
                        .getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
            String content = "receive extented notification message: " + extras.getString(BOUND_KEY.pushMsgKey);
            LogUtil.i(TAG, content);
        }
        super.onEvent(context, event, extras);
    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        try {
            String content = "The current push status： " + (pushState ? "Connected" : "Disconnected");
            LogUtil.i(TAG, content);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
    }
}
