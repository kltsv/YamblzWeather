package com.ringov.yamblzweather.data.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.domain.exceptions.NoInternetConnectionException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    /**
     * Check if device has internet connection and throw corresponding exception
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!isOnline()) {
            throw new NoInternetConnectionException();
        }
        Request request = chain.request();
        return chain.proceed(request);
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
