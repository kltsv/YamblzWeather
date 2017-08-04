package com.ivanantsiferov.yamblzweather;

import com.ringov.yamblzweather.BuildConfig;
import com.ringov.yamblzweather.Config;
import com.ringov.yamblzweather.data.networking.BaseInterceptor;
import com.ringov.yamblzweather.data.networking.ConnectivityInterceptor;
import com.ringov.yamblzweather.data.networking.WeatherAPI;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.Assert.assertTrue;

public class WeatherApiTest {

    private static final int CITY_ID = 524901;

    private WeatherAPI weatherAPI;

    @Before
    public void prepare() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BaseInterceptor())
                .addInterceptor(new ConnectivityInterceptor())
                .readTimeout(Config.TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Config.TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        weatherAPI = retrofit.create(WeatherAPI.class);
    }

    @Test
    public void getWeather() {
        weatherAPI.getWeather(CITY_ID)
                .subscribe((responseWeather, throwable) -> assertTrue(responseWeather.getName().equals("Moscow")));
    }

    @Test
    public void getForecast() {
        weatherAPI.getForecast(CITY_ID)
                .subscribe((forecastResponse, throwable) -> assertTrue(forecastResponse.getForecast().size() > 0));
    }

    @Test
    public void getDailyForecast() {
        weatherAPI.getDailyForecast(CITY_ID)
                .subscribe((forecastResponse, throwable) -> assertTrue(forecastResponse.getForecast().size() > 0));
    }
}
