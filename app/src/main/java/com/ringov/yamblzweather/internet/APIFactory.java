package com.ringov.yamblzweather.internet;

import android.support.annotation.NonNull;

import com.ringov.yamblzweather.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by ringov on 14.07.17.
 */

public class APIFactory {

    @NonNull
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new BaseInterceptor())
            .build();

    @NonNull
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build();

    @NonNull
    public static <T> T getRetrofitService(Class<T> tClass) {
        return retrofit.create(tClass);
    }
}
