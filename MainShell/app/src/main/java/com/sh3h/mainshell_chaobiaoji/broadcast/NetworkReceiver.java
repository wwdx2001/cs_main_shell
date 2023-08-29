package com.sh3h.mainshell_chaobiaoji.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.R;
import com.sh3h.mobileutil.util.ApplicationsUtil;


public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                // 获取当前网络状态信息
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    if (context instanceof MainApplication) {
                        ((MainApplication)context).checkToken();
                    }
                } else {
                    ApplicationsUtil.showMessage(context, R.string.text_network_not_connected);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
