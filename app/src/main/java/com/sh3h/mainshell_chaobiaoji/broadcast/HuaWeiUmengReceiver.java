package com.sh3h.mainshell_chaobiaoji.broadcast;


import android.content.Context;
import android.util.Log;

import org.android.agoo.huawei.HuaWeiReceiver;
import org.android.agoo.huawei.HuaWeiRegister;

public class HuaWeiUmengReceiver extends HuaWeiReceiver {
    private static final String TAG = "HuaWeiUmengReceiver";

    public HuaWeiUmengReceiver() {
        super();
    }

    @Override
    public void onPushMsg(Context var1, byte[] var2, String var3) {
        super.onPushMsg(var1, var2, var3);
        Log.i(TAG, "<<<---onPushMsg--->>>");
    }

    @Override
    public void onToken(Context var1, String var2) {
        super.onToken(var1, var2);
        Log.i(TAG, "<<<---onToken--->>>");
    }

    public static void register(final Context var0) {
        HuaWeiRegister.register(var0);
        enableReceiveNormalMsg(var0, true);
    }
}
