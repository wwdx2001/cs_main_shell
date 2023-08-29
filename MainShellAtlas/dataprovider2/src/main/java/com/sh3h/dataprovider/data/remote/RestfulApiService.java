package com.sh3h.dataprovider.data.remote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

//import com.sh3h.datautil.BuildConfig;

//import com.sh3h.datautil.BuildConfig;

public interface RestfulApiService {

    /********
     * Helper class that sets up a new services
     *******/
    class Factory {
        public static RestfulApiService newInstance(Bus bus, String baseUrl) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.interceptors().add(new UnauthorisedInterceptor(bus));
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(true ? HttpLoggingInterceptor.Level.BODY
                    : HttpLoggingInterceptor.Level.NONE); // BuildConfig.DEBUG
            okHttpClient.interceptors().add(logging);

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
        }
    }
}
