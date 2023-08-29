package com.mainshell.datautil.data.remote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mainshell.datautil.data.entity.DUConfigXmlFile;
import com.mainshell.datautil.data.entity.DUDeviceInfo;
import com.mainshell.datautil.data.entity.DULocationTrack;
import com.mainshell.datautil.data.entity.DULoginInfo;
import com.mainshell.datautil.data.entity.base.DUEntitiesResult;
import com.mainshell.datautil.data.entity.base.DUEntityResult;
import com.mainshell.datautil.data.entity.result.DUApkInfoResult;
import com.mainshell.datautil.data.entity.result.DUCheckResult;
import com.mainshell.datautil.data.entity.result.DUDeviceResult;
import com.mainshell.datautil.data.entity.result.DULoginResult;
import com.mainshell.datautil.data.entity.result.DUTrackResult;
import com.mainshell.datautil.data.entity.result.DUTracksResult;
import com.mainshell.datautil.data.entity.result.DUUserResult;
import com.mainshell.datautil.data.entity.result.DUWordsResult;
import com.squareup.otto.Bus;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

//import com.mainshell.datautil.BuildConfig;

//import com.mainshell.datautil.BuildConfig;

public interface RestfulApiService {

    /**
     * /** 用户登录
     **/
    @POST("auth")
    Observable<DUEntityResult<DULoginResult>> login(@Body DULoginInfo loginInfo);

    /**
     * 根据userId获取用户信息
     **/
    @GET("user/{userId}")
    Observable<DUEntityResult<DUUserResult>> getUserInfo(@Path("userId") int userId);

    /**
     * 根据deviceId获取设备信息
     **/
    @GET("device/{deviceId}")
    Observable<DUEntityResult<DUDeviceResult>> getDeviceInfo(@Path("deviceId") int deviceId);

    /**
     * 激活设备
     **/
    @POST("devices/register")
    Observable<DUEntityResult<DUDeviceResult>> register(@Body DUDeviceInfo deviceInfo);

    /**
     * 轨迹查询
     **/
    @GET("tracks?userId={userId}&deviceId={deviceId}&start={start}&end={end}")
    Observable<DUEntitiesResult<DUTracksResult>> getTracks(@Path("userId") int userId, @Path("deviceId") int deviceId, @Path("start") int start, @Path("end") int end);

    /**
     * 位置追踪
     **/
    @POST("track")
    Observable<DUEntityResult<DUTrackResult>> sendTrack(@Body DULocationTrack duLocationTrack);

    /**
     * 检查APP更新
     **/
    @GET("update/app/check")
    Observable<DUEntityResult<DUCheckResult>> getAppCheck(@Query("appId") String appId, @Query("ver") int ver);

    /**
     * 检查数据包更新
     **/
    @GET("update/data/check")
    Observable<DUEntityResult<DUCheckResult>> getDataCheck(@Query("appId") String appId, @Query("ver") int ver);

    /**
     * 检查配置信息更新
     **/
    @GET("update/configure/check")
    Observable<DUEntitiesResult<DUConfigXmlFile.Component>> getConfigCheck(@Query("userId") int userId);

    /**
     * 获取APK列表
     **/
    @GET("apks")
    Observable<DUEntitiesResult<DUApkInfoResult>> getApkList(@Query("userId") int userId, @Query("deviceId") String deviceId);

    /**
     * 获取词语信息
     **/
    @GET("words")
    Observable<DUEntitiesResult<DUWordsResult>> getWords(@Query("group") String group);

    /**
     * 获取最新公告
     **/
    @GET("api/WapMobile")
    Observable<DUEntityResult<?>> getWapMobile();

    /********
     * Helper class that sets up a new services
     *******/
    class Factory {
        public static RestfulApiService newInstance(Bus bus, String baseUrl) {
            try {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY); // HttpLoggingInterceptor.Level.NONE
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .addInterceptor(new UnauthorisedInterceptor(bus))
                        .build();

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                return retrofit.create(RestfulApiService.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
