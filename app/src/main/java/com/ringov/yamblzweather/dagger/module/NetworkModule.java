package com.ringov.yamblzweather.dagger.module;

import android.content.Context;

import com.ringov.yamblzweather.BuildConfig;
import com.ringov.yamblzweather.Const;
import com.ringov.yamblzweather.data.networking.BaseInterceptor;
import com.ringov.yamblzweather.data.networking.ConnectivityInterceptor;
import com.ringov.yamblzweather.data.networking.WeatherAPI;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ApplicationModule.class)
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        // TODO remove on release
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(new BaseInterceptor())
                .addInterceptor(new ConnectivityInterceptor(context))
                .addInterceptor(loggingInterceptor)
                .readTimeout(Const.TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Const.TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    WeatherAPI provideWeatherAPI(Retrofit retrofit) {
        return retrofit.create(WeatherAPI.class);
    }
}
