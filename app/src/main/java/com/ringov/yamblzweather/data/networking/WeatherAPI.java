package com.ringov.yamblzweather.data.networking;

import com.ringov.yamblzweather.data.networking.data.ForecastResponse;
import com.ringov.yamblzweather.data.networking.data.ResponseWeather;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather")
    Single<ResponseWeather> getWeather(@Query("id") int cityId);

    @GET("forecast")
    Single<ForecastResponse> getForecast(@Query("id") int cityId);

    @GET("forecast/daily")
    Single<ForecastResponse> getDailyForecast(@Query("id") int cityId);
}
