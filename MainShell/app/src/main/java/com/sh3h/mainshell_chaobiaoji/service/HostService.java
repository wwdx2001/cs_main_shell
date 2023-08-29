package com.sh3h.mainshell_chaobiaoji.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.sh3h.ipc.IMainService;
//import com.sh3h.ipc.IRemoteService;
import com.sh3h.ipc.IRemoteServiceCallback;
import com.sh3h.ipc.location.MyLocation;
import com.sh3h.ipc.module.MyModule;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HostService extends Service {
    public static final String BINDING_NAME = "bindingName";
    private static final String TAG = "HostService";
    private static final int REPORT_MSG = 1;

    private final RemoteCallbackList<IRemoteServiceCallback> mCallbacks = new RemoteCallbackList<IRemoteServiceCallback>();

    private long timeError = 0;

    private List<Integer> pIds = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(TAG, "---HostService---onBind()");

//        if (IMainService.class.getName().equals(intent.getAction())) {
//            return mMainBinder;
//        } else if (IRemoteService.class.getName().equals(intent.getAction())) {
//            return mRemoteBinder;
//        } else {
//            return null;
//        }

        String bindingName = intent.getStringExtra(BINDING_NAME);
        if (TextUtil.isNullOrEmpty(bindingName)) {
            LogUtil.e(TAG, "bindingName is null");
            return null;
        } else if (IMainService.class.getName().equals(bindingName)) {
            LogUtil.i(TAG, "---HostService---IMainService");
            return mMainBinder;
        } else {
            LogUtil.e(TAG, "bindingName isn't matching");
            return null;
        }
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        LogUtil.i(TAG, "---HostService---onCreate()");
        super.onCreate();

        int pId = Process.myPid();
        boolean found = false;
        for (Integer integer : pIds) {
            if (pId == integer) {
                found = true;
                break;
            }
        }

        if (!found) {
            pIds.add(pId);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.i(TAG, "---HostService---onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        LogUtil.i(TAG, "---HostService---onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        LogUtil.i(TAG, "---HostService---onDestroy()");
        mCallbacks.kill();
        super.onDestroy();
    }

    private final IMainService.Stub mMainBinder = new IMainService.Stub() {
        //private MyLocation myLocation;

        @Override
        public void setLocation(MyLocation myLocation) throws RemoteException {
            Log.i(TAG, "---IMainService---setLocation()");
            //this.myLocation = myLocation;
            locationChanged(myLocation);
//            try {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("packageName", "com.sh3h.meterreading");
//                jsonObject.put("activity", "com.sh3h.meterreading.ui.ListActivity");
//                jsonObject.put("count", 10);
//                moduleChanged(new MyModule(jsonObject.toString()));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

//        @Override
//        public MyLocation getLocation() {
//            LogUtil.i(TAG, "---IMainService---getLocation()");
//            return this.myLocation;
//        }

        @Override
        public void setMyModule(MyModule myModule) {
            moduleChanged(myModule);
        }

        @Override
        public void exitSystem() {
            exit();
        }

        @Override
        public int[] getPids() {
            int size = pIds.size();
            int[] pIdArray = new int[size];
            for (int i = 0; i < size; i++) {
                pIdArray[i] = pIds.get(i);
            }
            return pIdArray;
        }

        @Override
        public void addPid(int pid) {
            pIds.add(pid);
        }

        @Override
        public void setTimeError(long error) {
            timeError = error;
        }

        @Override
        public long getCurrentTime() {
            long time = new Date().getTime();
            return time + timeError;
        }

        public void registerCallback(IRemoteServiceCallback cb) {
            if (cb != null) {
                mCallbacks.register(cb);
            }
        }

        public void unregisterCallback(IRemoteServiceCallback cb) {
            if (cb != null) {
                mCallbacks.unregister(cb);
            }
        }
    };

//    private final IRemoteService.Stub mRemoteBinder = new IRemoteService.Stub() {
//        public void registerCallback(IRemoteServiceCallback cb) {
//            if (cb != null) {
//                mCallbacks.register(cb);
//            }
//        }
//
//        public void unregisterCallback(IRemoteServiceCallback cb) {
//            if (cb != null) {
//                mCallbacks.unregister(cb);
//            }
//        }
//    };

    /**
     * Our Handler used to execute operations on the main thread.  This is used
     * to schedule increments of our value.
     */
//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case REPORT_MSG: {
//                    LogUtil.i(TAG, "---handleMessage---1");
//                    // Up it goes.
//                    Object obj = msg.obj;
//                    if (obj instanceof MyModule) {
//                        LogUtil.i(TAG, "---handleMessage---2");
//                        MyModule myModule = (MyModule) obj;
//                        // Broadcast to all clients the new value.
//                        final int N = mCallbacks.beginBroadcast();
//                        for (int i = 0; i < N; i++) {
//                            try {
//                                mCallbacks.getBroadcastItem(i).moduleChanged(myModule);
//                                LogUtil.i(TAG, "---handleMessage---3");
//                            } catch (RemoteException e) {
//                                e.printStackTrace();
//                                LogUtil.e(TAG, e.getMessage());
//                            }
//                        }
//                        mCallbacks.finishBroadcast();
//                    }
//                }
//                break;
//                default:
//                    super.handleMessage(msg);
//            }
//        }
//    };
    private void locationChanged(MyLocation myLocation) {
        if (myLocation != null) {
            Log.i(TAG, "locationChanged 1");
            // Broadcast to all clients the new value.
            final int N = mCallbacks.beginBroadcast();
            for (int i = 0; i < N; i++) {
                try {
                    mCallbacks.getBroadcastItem(i).locationChanged(myLocation);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    LogUtil.e(TAG, e.getMessage());
                }
            }
            mCallbacks.finishBroadcast();
        } else {
            LogUtil.e(TAG, "myLocation is null");
        }
    }

    private void moduleChanged(MyModule myModule) {
        if (myModule != null) {
            LogUtil.i(TAG, "moduleChanged 1");
            // Broadcast to all clients the new value.
            final int N = mCallbacks.beginBroadcast();
            for (int i = 0; i < N; i++) {
                try {
                    mCallbacks.getBroadcastItem(i).moduleChanged(myModule);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    LogUtil.e(TAG, e.getMessage());
                }
            }
            mCallbacks.finishBroadcast();
        } else {
            LogUtil.e(TAG, "myModule is null");
        }
    }

    private void exit() {
        LogUtil.i(TAG, "exit 1");
        // Broadcast to all clients the new value.
        final int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).exitSystem();
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.e(TAG, e.getMessage());
            }
        }
        mCallbacks.finishBroadcast();
    }
}
