package com.ringov.yamblzweather.data.networking;

import com.ringov.yamblzweather.data.networking.data.ResponseWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    Observable<ResponseWeather> getWeather(@Query("id") int cityId);

    @GET("weather")
    Observable<ResponseWeather> getWeather(@Query("q") String cityName);
}
