package com.sh3h.datautil.data.remote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sh3h.datautil.data.entity.base.DUEntityResult;
import com.sh3h.datautil.data.entity.result.DUCoordinateGasGroupResult;
import com.sh3h.datautil.data.entity.result.DUCoordinateJiangMenResult;
import com.sh3h.datautil.data.entity.result.DUCoordinateYiWuResult;
import com.squareup.otto.Bus;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface LocationApiService {
    @GET("tasks/locate?")
    Observable<DUEntityResult<DUCoordinateGasGroupResult>> transformCoordinateForGasGroup(@Query("lon") String longitude, @Query("lat") String latitude);

    @GET("CoorTransREST.svc/bltoxy?")
    Observable<DUCoordinateYiWuResult> transformCoordinateForYiWu(@Query("b1") double b1, @Query("l1") double l1);

    @GET("/ServiceEngine/rest/services/SearchServer/transform?f=json")
    Observable<DUCoordinateJiangMenResult> transformCoordinateForJiangMen(@Query("x") double x, @Query("y") double y);

    class Factory {
        public static LocationApiService newInstance(Bus bus, String baseUrl) {
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
                return retrofit.create(LocationApiService.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
