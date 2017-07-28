package com.ringov.yamblzweather.model.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.model.networking.exceptions.NoInternetConnectionException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ringov on 20.07.17.
 */

public class ConnectivityInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isOnline()) {
            throw new NoInternetConnectionException(App.getContext().getString(R.string.no_internet_connection));
        }
        Request request = chain.request();
        return chain.proceed(request);
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}