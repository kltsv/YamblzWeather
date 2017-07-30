package com.ringov.yamblzweather.model.networking;

import com.ringov.yamblzweather.model.networking.data.ResponseWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ringov on 14.07.17.
 */

public interface WeatherService {

    @GET("weather")
    Observable<ResponseWeather> getWeather(@Query("id") int cityId);

    @GET("weather")
    Observable<ResponseWeather> getWeather(@Query("q") String cityName);
}
