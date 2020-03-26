package com.ringov.yamblzweather.data.networking;

import android.support.annotation.NonNull;

import com.ringov.yamblzweather.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseInterceptor implements Interceptor {

    /**
     * Adds api key params to each HTTP request
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl newUrl = request.url().newBuilder()
                .addQueryParameter(APIContract.PARAM_API_KEY, BuildConfig.API_KEY)
                .build();
        Request newRequest = request.newBuilder().url(newUrl).build();
        return chain.proceed(newRequest);
    }
}
