package com.sh3h.mainshell_chaobiaoji.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.sh3h.datautil.data.DataManager;
import com.sh3h.datautil.data.entity.result.DUCoordinateResult;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.mainshell_chaobiaoji.MainApplication;
import com.sh3h.mainshell_chaobiaoji.util.CoordTransformUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.sh3h.mainshell_chaobiaoji.AppChannel.GAS_GROUP_CHANNEL;
import static com.sh3h.mainshell_chaobiaoji.AppChannel.JIANGMENG_CHANNEL;
import static com.sh3h.mainshell_chaobiaoji.AppChannel.YIWU_CHANNEL;

@Singleton
public class GpsLocation {
    public static class MRLocation {
        private double mLongitude;
        private double mLatitude;
        private float mDirection;
        private float mAccuracy;
        private double mAltitude;
        private float mSpeed;
        private double mLocalLongitude;
        private double mLocalLatitude;

        public MRLocation() {
            mLongitude = 0.0;
            mLatitude = 0.0;
            mDirection = 0.0f;
            mAccuracy = 0.0f;
        }

        public MRLocation(double longitude,
                          double latitude,
                          float direction,
                          float accuracy,
                          double localLongitude,
                          double localLatitude) {
            mLongitude = longitude;
            mLatitude = latitude;
            mDirection = direction;
            mAccuracy = accuracy;
            mLocalLongitude = localLongitude;
            mLocalLatitude = localLatitude;
        }

        public void setLongitude(double longitude) {
            mLongitude = longitude;
        }

        public double getLongitude() {
            return mLongitude;
        }

        public void setLatitude(double latitude) {
            mLatitude = latitude;
        }

        public double getLatitude() {
            return mLatitude;
        }

        public void setDirection(float direction) {
            mDirection = direction;
        }

        public float getDirection() {
            return mDirection;
        }

        public void setAccuracy(float accuracy) {
            mAccuracy = accuracy;
        }

        public float getAccuracy() {
            return mAccuracy;
        }

        public float getSpeed() {
            return mSpeed;
        }

        public void setSpeed(float speed) {
            this.mSpeed = mSpeed;
        }

        public double getAltitude() {
            return mAltitude;
        }

        public void setAltitude(double altitude) {
            this.mAltitude = altitude;
        }

        public double getLocalLongitude() {
            return mLocalLongitude;
        }

        public void setLocalLongitude(double mLocalLongitude) {
            this.mLocalLongitude = mLocalLongitude;
        }

        public double getLocalLatitude() {
            return mLocalLatitude;
        }

        public void setLocalLatitude(double mLocalLatitude) {
            this.mLocalLatitude = mLocalLatitude;
        }
    }

    public interface MRLocationListener {
        void updateLocation(boolean isLocated, MRLocation mrLocation, long time);
    }

    private static final String TAG = "GpsLocation";

    private final Context mContext;
    private LocationClient mLocationClient;
    //private MyLocationListener mMyLocationListener;
    private MyBDLocationListener mMyBDLocationListener;
    private LocationManager mLocationManager;
    private List<MRLocationListener> mMRLocationListenerList;
    private boolean mIsGpsLocated;
    private MRLocation mMRLocation;
    private boolean mIsInit;
    private String mLocationUrl;
    private int mScanSpan;

    @Inject
    public GpsLocation(@ApplicationContext Context context) {
        mContext = context;
        mLocationClient = null;
        //mMyLocationListener = null;
        mLocationManager = null;
        mMRLocationListenerList = null;
        mIsGpsLocated = false;
        mMRLocation = null;
        mIsInit = false;
        mLocationUrl = null;
        mScanSpan = 0;

        LogUtil.i(TAG, "---GpsLocation----");
    }

    /**
     * @param isBaiduLoc
     */
    public boolean initLocation(boolean isBaiduLoc, String locationUrl, int scanSpan) {
        LogUtil.i(TAG, "---initLocation 1----");
        if (mIsInit) {
            LogUtil.i(TAG, "---initLocation 2----");
            return false;
        }

        mIsInit = true;
        mMRLocationListenerList = new ArrayList<>();
        mIsGpsLocated = false;
        mMRLocation = new MRLocation();
        //mLock = new byte[0];
        mLocationUrl = locationUrl;
        mScanSpan = scanSpan;
        if (mScanSpan <= 1000) {
            mScanSpan = 1000;
        }

        // gps location & listener
        if (isBaiduLoc) {
            mLocationClient = new LocationClient(mContext);
            //mMyLocationListener = new MyLocationListener();
            mMyBDLocationListener = new MyBDLocationListener();
            mLocationClient.registerLocationListener(mMyBDLocationListener);
            setLocationOption();
            mLocationClient.start();
        } else {
            try {
                mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        mScanSpan, 1, mGpsLocationListener);
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        mScanSpan, 1, mNetworkLocationListener);
                //mLocationManager.addGpsStatusListener(mMyGpsStatusListener);
            } catch (SecurityException e) {
                e.printStackTrace();
                mLocationManager = null;
            }
        }

        LogUtil.i(TAG, "---initLocation 3----");
        return true;
    }

    /**
     * @param isBaiduLoc
     */
    public boolean destroyLocation(boolean isBaiduLoc) {
        LogUtil.i(TAG, "---destroyLocation 1----");
        if (!mIsInit) {
            LogUtil.i(TAG, "---destroyLocation 2----");
            return false;
        }

        mIsInit = false;
        if (isBaiduLoc) {
            if (mMyBDLocationListener != null) {
                mLocationClient.unRegisterLocationListener(mMyBDLocationListener);
                mLocationClient.stop();
            }
        } else {
            try {
                if (mLocationManager != null) {
                    mLocationManager.removeUpdates(mGpsLocationListener);
                    mLocationManager.removeUpdates(mNetworkLocationListener);
                    //mLocationManager.removeGpsStatusListener(mMyGpsStatusListener);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        LogUtil.i(TAG, "---destroyLocation 3----");
        return true;
    }

    /**
     * @param mrLocationListener
     */
    public void registerMRLocationListener(MRLocationListener mrLocationListener) {
        if (!mIsInit) {
            return;
        }

        if (mrLocationListener != null) {
            mMRLocationListenerList.add(mrLocationListener);
        }
    }

    /**
     * @param mrLocationListener
     */
    public void unRegisterMRLocationListener(MRLocationListener mrLocationListener) {
        if (!mIsInit) {
            return;
        }

        if (mrLocationListener != null) {
            mMRLocationListenerList.remove(mrLocationListener);
        }
    }

    /**
     * @return
     */
    public MRLocation getMRLocation() {
        return mMRLocation;
    }

    /**
     * @return
     */
    public boolean isGpsLocated() {
        return mIsGpsLocated;
    }

    /**
     * set location options for location client
     */
    private void setLocationOption() {
        if (mLocationClient != null) {
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
            option.setCoorType("gcj02");//返回的定位结果是百度经纬度，百度手机地图对外接口中的坐标系默认是bd09ll
            option.setScanSpan(mScanSpan);//设置发起定位请求的间隔时间为1000ms
            option.setIsNeedAddress(false);//返回的定位结果包含地址信息
            option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
            option.setOpenGps(true);//可选，默认false,设置是否使用gps
            option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
            option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mLocationClient.setLocOption(option);
        }
    }

    /**
     *
     */
    private LocationListener mGpsLocationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            LogUtil.i(TAG, "---onLocationChanged(gps)----");
            updateLocation(location);
        }
    };

    /**
     *
     */
    private LocationListener mNetworkLocationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onLocationChanged(Location location) {
            LogUtil.i(TAG, "---onLocationChanged(network)----");
            updateLocation(location);
        }
    };

    /**
     * @param location
     */
    private void updateLocation(Location location) {
        LogUtil.i(TAG, "---updateLocation----");

        mIsGpsLocated = false;
        mMRLocation = new MRLocation();
        if (location == null) {
            return;
        }

        mIsGpsLocated = true;
        mMRLocation.setLongitude(location.getLongitude());
        mMRLocation.setLatitude(location.getLatitude());

        if (location.hasBearing()) {
            mMRLocation.setDirection(location.getBearing());
        }

        if (location.hasAccuracy()) {
            mMRLocation.setAccuracy(location.getAccuracy());
        }

        for (MRLocationListener mrLocationListener : mMRLocationListenerList) {
            mrLocationListener.updateLocation(mIsGpsLocated, mMRLocation, location.getTime());
        }
    }

    /**
     * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
     */
//    private class MyLocationListener implements BDLocationListener {
//        private boolean isTransformingFinish = true;
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            //LogUtil.i(TAG, "---onReceiveLocation----");
//            if (location == null) {
//                return;
//            }
//
//            if ((location.getLocType() == BDLocation.TypeGpsLocation)
//                    || (location.getLocType() == BDLocation.TypeNetWorkLocation)
//                    || (location.getLocType() == BDLocation.TypeOffLineLocation)) {
//                //double[] gcj02 = CoordTransformUtil.bd09togcj02(120.688539,28.008895);
//                //double[] wgs84 = CoordTransformUtil.gcj02towgs84(gcj02[0], gcj02[1]);
//                //double[] gcj02 = new double[]{location.getLongitude(), location.getLatitude()};
//                double[] wgs84 = CoordTransformUtil.gcj02towgs84(location.getLongitude(), location.getLatitude());
//                Log.i(TAG, "---onReceiveLocation wgs84[" + wgs84[0] + ", " + wgs84[1] + "] time: " + location.getTime());
//                //double[] xy = TransformUtil.transformBL2XY(wgs84[1], wgs84[0]);
//                /*MRLocation mrLocation = new MRLocation();
//                mrLocation.setLongitude(wgs84[0]);
//                mrLocation.setLatitude(wgs84[1]);
//
////                    LogUtil.i(TAG, "---onReceiveLocation gcj02["
////                            + gcj02[0] + ", " + gcj02[1] + "] wgs84["
////                            + wgs84[0] + ", " + wgs84[1] + "] xy["
////                            + xy[0] + ", " + xy[1] + "]");
//
//                mrLocation.setDirection(location.getDirection());
//                mrLocation.setAccuracy(location.getRadius());
//                mrLocation.setAltitude(location.getAltitude()); // 高度
//                mrLocation.setSpeed(location.getSpeed());// 速度
//
////                Observable.create(new Observable.OnSubscribe<Boolean>() {
////                    @Override
////                    public void call(Subscriber<? super Boolean> subscriber) {
////
////                    }
////                }).observeOn(AndroidSchedulers.mainThread())
////                        .subscribeOn(Schedulers.io())
////                        .subscribe(new Subscriber<Boolean>() {
////                            @Override
////                            public void onCompleted() {
////
////                            }
////
////                            @Override
////                            public void onError(Throwable e) {
////
////                            }
////
////                            @Override
////                            public void onNext(Boolean aBoolean) {
////
////                            }
////                        });
//                */
//                long time = getTime(location.getTime());
//                transformLocation(wgs84[0], wgs84[1], location.getDirection(),
//                        location.getRadius(), location.getAltitude(), location.getSpeed(), time);
//            }
//        }
//
////        @Override
////        public void onConnectHotSpotMessage(String var1, int var2) {
////
////        }
//
//        private void transformLocation(final double longitude, final double latitude,
//                                       final float direction, final float accuracy,
//                                       final double altitude, final float speed,
//                                       final long time) {
//            if (TextUtil.isNullOrEmpty(mLocationUrl)) {
//                setLocation(longitude, latitude, direction, accuracy, altitude, speed, time, 0, 0);
//            } else if (mLocationUrl.equals("baidu")) {
//                double[] gcj02 = CoordTransformUtil.wgs84togcj02(longitude, latitude);
//                double[] bd09 = CoordTransformUtil.gcj02tobd09(gcj02[0], gcj02[1]);
//                setLocation(longitude, latitude, direction, accuracy, altitude, speed, time, bd09[0], bd09[1]);
//            } else {
//                Log.i(TAG, "---transformLocation begin---");
//                if (!isTransformingFinish) {
//                    Log.i(TAG, "---transformLocation busy---");
//                    return;
//                }
//
//                isTransformingFinish = false;
//                String appChannel = MainApplication.get(mContext).getAppChannel();
//                DataManager dataManager = MainApplication.get(mContext).getDataManager();
//                Observable<DUCoordinateResult> observable;
//                if (TextUtil.isNullOrEmpty(appChannel)) {
//                    observable = null;
//                } else if (appChannel.equalsIgnoreCase(GAS_GROUP_CHANNEL)) {
//                    observable = null;//dataManager.transformCoordinateForGasGroup(longitude, latitude);
//                } else if (appChannel.equalsIgnoreCase(JIANGMENG_CHANNEL)) {
//                    observable = dataManager.transformCoordinateForJiangMen(longitude, latitude);
//                } else if (appChannel.equalsIgnoreCase(YIWU_CHANNEL)) {
//                    observable = dataManager.transformCoordinateForYiWu(longitude, latitude);
//                } else {
//                    observable = null;
//                }
//
//                if (observable == null) {
//                    setLocation(longitude, latitude, direction, accuracy, altitude, speed, time, 0, 0);
//                    return;
//                }
//
//                observable.observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(new Subscriber<DUCoordinateResult>() {
//                            @Override
//                            public void onCompleted() {
//                                Log.i(TAG, "---transformLocation onCompleted---");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.i(TAG, String.format("---transformLocation onError: %s---", e.getMessage()));
//                                //setLocation(longitude, latitude, direction, accuracy, altitude, speed, time, 0, 0);
//                                isTransformingFinish = true;
//                            }
//
//                            @Override
//                            public void onNext(DUCoordinateResult duCoordinateResult) {
//                                Log.i(TAG, String.format("---transformLocation onNext---"));
//                                setLocation(longitude, latitude, direction, accuracy,
//                                        altitude, speed, time,
//                                        duCoordinateResult.getX(),
//                                        duCoordinateResult.getY());
//                            }
//                        });
//            }
//        }
//
//        private void setLocation(double longitude, double latitude, float direction,
//                                 float accuracy, double altitude, float speed, long time,
//                                 double localLongitude, double localLatitude) {
//            mIsGpsLocated = true;
//            mMRLocation = new MRLocation(longitude, latitude, direction, accuracy, localLongitude, localLatitude);
//            for (MRLocationListener mrLocationListener : mMRLocationListenerList) {
//                mrLocationListener.updateLocation(mIsGpsLocated, mMRLocation, time);
//            }
//            isTransformingFinish = true;
//        }
//
//        private long getTime(String time) {
//            try {
//                return TextUtil.format(time, TextUtil.FORMAT_FULL_DATETIME).getTime();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return 0;
//            }
//        }
//    }

    private class MyBDLocationListener extends BDAbstractLocationListener {
        private boolean isTransformingFinish = true;

        @Override
        public void onReceiveLocation(BDLocation location) {
            if ((location.getLocType() == BDLocation.TypeGpsLocation)
                    || (location.getLocType() == BDLocation.TypeNetWorkLocation)
                    || (location.getLocType() == BDLocation.TypeOffLineLocation)) {
                //double[] gcj02 = CoordTransformUtil.bd09togcj02(120.688539,28.008895);
                //double[] wgs84 = CoordTransformUtil.gcj02towgs84(gcj02[0], gcj02[1]);
                //double[] gcj02 = new double[]{location.getLongitude(), location.getLatitude()};
                double[] wgs84 = CoordTransformUtil.gcj02towgs84(location.getLongitude(), location.getLatitude());
                Log.i(TAG, "---onReceiveLocation wgs84[" + wgs84[0] + ", " + wgs84[1] + "] time: " + location.getTime());
                //double[] xy = TransformUtil.transformBL2XY(wgs84[1], wgs84[0]);
                long time = getTime(location.getTime());
                transformLocation(wgs84[0], wgs84[1], location.getDirection(),
                        location.getRadius(), location.getAltitude(), location.getSpeed(), time);
            }
        }

        private void transformLocation(final double longitude, final double latitude,
                                       final float direction, final float accuracy,
                                       final double altitude, final float speed,
                                       final long time) {
            if (TextUtil.isNullOrEmpty(mLocationUrl)) {
                setLocation(longitude, latitude, direction, accuracy, altitude, speed, time, 0, 0);
            } else if (mLocationUrl.equals("baidu")) {
                double[] gcj02 = CoordTransformUtil.wgs84togcj02(longitude, latitude);
                double[] bd09 = CoordTransformUtil.gcj02tobd09(gcj02[0], gcj02[1]);
                setLocation(longitude, latitude, direction, accuracy, altitude, speed, time, bd09[0], bd09[1]);
            } else {
                Log.i(TAG, "---transformLocation begin---");
                if (!isTransformingFinish) {
                    Log.i(TAG, "---transformLocation busy---");
                    return;
                }

                isTransformingFinish = false;
                String appChannel = MainApplication.get(mContext).getAppChannel();
                DataManager dataManager = MainApplication.get(mContext).getDataManager();
                Observable<DUCoordinateResult> observable;
                if (TextUtil.isNullOrEmpty(appChannel)) {
                    observable = null;
                } else if (appChannel.equalsIgnoreCase(GAS_GROUP_CHANNEL)) {
                    observable = null;//dataManager.transformCoordinateForGasGroup(longitude, latitude);
                } else if (appChannel.equalsIgnoreCase(JIANGMENG_CHANNEL)) {
                    observable = dataManager.transformCoordinateForJiangMen(longitude, latitude);
                } else if (appChannel.equalsIgnoreCase(YIWU_CHANNEL)) {
                    observable = dataManager.transformCoordinateForYiWu(longitude, latitude);
                } else {
                    observable = null;
                }

                if (observable == null) {
                    setLocation(longitude, latitude, direction, accuracy, altitude, speed, time, 0, 0);
                    return;
                }

                observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<DUCoordinateResult>() {
                            @Override
                            public void onCompleted() {
                                Log.i(TAG, "---transformLocation onCompleted---");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, String.format("---transformLocation onError: %s---", e.getMessage()));
                                //setLocation(longitude, latitude, direction, accuracy, altitude, speed, time, 0, 0);
                                isTransformingFinish = true;
                            }

                            @Override
                            public void onNext(DUCoordinateResult duCoordinateResult) {
                                Log.i(TAG, String.format("---transformLocation onNext---"));
                                setLocation(longitude, latitude, direction, accuracy,
                                        altitude, speed, time,
                                        duCoordinateResult.getX(),
                                        duCoordinateResult.getY());
                            }
                        });
            }
        }

        private void setLocation(double longitude, double latitude, float direction,
                                 float accuracy, double altitude, float speed, long time,
                                 double localLongitude, double localLatitude) {
            mIsGpsLocated = true;
            mMRLocation = new MRLocation(longitude, latitude, direction, accuracy, localLongitude, localLatitude);
            for (MRLocationListener mrLocationListener : mMRLocationListenerList) {
                mrLocationListener.updateLocation(mIsGpsLocated, mMRLocation, time);
            }
            isTransformingFinish = true;
        }

        private long getTime(String time) {
            try {
                return TextUtil.format(time, TextUtil.FORMAT_FULL_DATETIME).getTime();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}
