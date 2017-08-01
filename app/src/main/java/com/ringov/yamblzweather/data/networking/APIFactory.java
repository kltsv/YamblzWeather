package com.ringov.yamblzweather.data.networking;

import android.support.annotation.NonNull;

import com.ringov.yamblzweather.BuildConfig;
import com.ringov.yamblzweather.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIFactory {

    @NonNull
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new BaseInterceptor())
            .addInterceptor(new ConnectivityInterceptor())
            .readTimeout(Config.TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(Config.TIMEOUT, TimeUnit.MILLISECONDS)
            .build();

    @NonNull
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @NonNull
    public static <T> T getRetrofitService(Class<T> tClass) {
        return retrofit.create(tClass);
    }
}
