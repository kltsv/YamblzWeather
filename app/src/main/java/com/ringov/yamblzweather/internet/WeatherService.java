package com.ringov.yamblzweather.internet;

import com.ringov.yamblzweather.internet.content.ResponseWeather;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ringov on 14.07.17.
 */

public interface WeatherService {

    @GET("weather")
    Observable<ResponseWeather> getWeather(@Query("id") int cityId);

}
