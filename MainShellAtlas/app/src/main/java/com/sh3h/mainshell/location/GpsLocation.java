package com.sh3h.mainshell.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.mainshell.datautil.injection.annotation.ApplicationContext;
import com.sh3h.mobileutil.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GpsLocation {
    public static class MRLocation {
        private double mLongitude;
        private double mLatitude;
        private float mDirection;
        private float mAccuracy;
        private double mAltitude;
        private float mSpeed;

        public MRLocation() {
            mLongitude = 0.0;
            mLatitude = 0.0;
            mDirection = 0.0f;
            mAccuracy = 0.0f;
        }

        public MRLocation(double longitude,
                          double latitude,
                          float direction,
                          float accuracy) {
            mLongitude = longitude;
            mLatitude = latitude;
            mDirection = direction;
            mAccuracy = accuracy;
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

    }

    public interface MRLocationListener {
        void updateLocation(boolean isLocated, MRLocation mrLocation);
    }

    private static final String TAG = "GpsLocation";

    private final Context mContext;
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;
    private LocationManager mLocationManager;
    private List<MRLocationListener> mMRLocationListenerList;
    private boolean mIsGpsLocated;
    private MRLocation mMRLocation;
    private int mReferenceCount;

    @Inject
    public GpsLocation(@ApplicationContext Context context) {
        mContext = context;
        mLocationClient = null;
        mMyLocationListener = null;
        mLocationManager = null;
        mMRLocationListenerList = null;
        mIsGpsLocated = false;
        mMRLocation = null;
        mReferenceCount = 0;

        LogUtil.i(TAG, "---GpsLocation----");
    }

    /**
     *
     * @param isBaiduLoc
     */
    public boolean initLocation(boolean isBaiduLoc) {
        LogUtil.i(TAG, "---initLocation 1----");
        if (mReferenceCount++ > 0) {
            LogUtil.i(TAG, "---initLocation 2----");
            return false;
        }

        mMRLocationListenerList = new ArrayList<>();
        mIsGpsLocated = false;
        mMRLocation = new MRLocation();
        //mLock = new byte[0];

        // gps location & listener
        if (isBaiduLoc) {
            mLocationClient = new LocationClient(mContext);
            mMyLocationListener = new MyLocationListener();
            mLocationClient.registerLocationListener(mMyLocationListener);
            setLocationOption();
            mLocationClient.start();
        }
        else {
            try {
                mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000, 1, mGpsLocationListener);
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000, 1, mNetworkLocationListener);
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
     *
     * @param isBaiduLoc
     */
    public boolean destroyLocation(boolean isBaiduLoc) {
        LogUtil.i(TAG, "---destroyLocation 1----");
        if (--mReferenceCount > 0) {
            LogUtil.i(TAG, "---destroyLocation 2----");
            return false;
        }

        mReferenceCount = 0;
        if (isBaiduLoc) {
            if (mMyLocationListener != null) {
                mLocationClient.unRegisterLocationListener(mMyLocationListener);
                mLocationClient.stop();
            }
        }
        else {
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
     *
     * @param mrLocationListener
     */
    public void registerMRLocationListener(MRLocationListener mrLocationListener) {
        if (mReferenceCount <= 0) {
            return;
        }

        if (mrLocationListener != null) {
            mMRLocationListenerList.add(mrLocationListener);
        }
    }

    /**
     *
     * @param mrLocationListener
     */
    public void unRegisterMRLocationListener(MRLocationListener mrLocationListener) {
        if (mReferenceCount <= 0) {
            return;
        }

        if (mrLocationListener != null) {
            mMRLocationListenerList.remove(mrLocationListener);
        }
    }

    /**
     *
     * @return
     */
    public MRLocation getMRLocation() {
        return mMRLocation;
    }

    /**
     *
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
            option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，百度手机地图对外接口中的坐标系默认是bd09ll
            option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
            option.setIsNeedAddress(false);//返回的定位结果包含地址信息
            option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
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
     *
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
            mrLocationListener.updateLocation(mIsGpsLocated, mMRLocation);
        }
    }

    /**
     * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
     */
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //LogUtil.i(TAG, "---onReceiveLocation----");

            mIsGpsLocated = false;
            mMRLocation = new MRLocation();
            if (location == null) {
                return;
            }

            if ((location.getLocType() == BDLocation.TypeGpsLocation)
                    || (location.getLocType() == BDLocation.TypeNetWorkLocation)
                    || (location.getLocType() == BDLocation.TypeOffLineLocation) ) {
                mIsGpsLocated = true;
                //double[] coordinate = CoordinateConvertUtil.transform(location.getLatitude(),
                //		location.getLongitude());
                //mGpsLocation.setLongitude(coordinate[1]);
                //mGpsLocation.setLatitude(coordinate[0]);

                mIsGpsLocated = true;
                mMRLocation.setLongitude(location.getLongitude()); // 经度
                mMRLocation.setLatitude(location.getLatitude());  // 纬度
                mMRLocation.setDirection(location.getDirection()); // 方向
                mMRLocation.setAccuracy(location.getRadius()); // 精度
                mMRLocation.setAltitude(location.getAltitude()); // 高度
                mMRLocation.setSpeed(location.getSpeed());// 速度


                for (MRLocationListener mrLocationListener : mMRLocationListenerList) {
                    mrLocationListener.updateLocation(mIsGpsLocated, mMRLocation);
                }
            }
        }
    }

}
