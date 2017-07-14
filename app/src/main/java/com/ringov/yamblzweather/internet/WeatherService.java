package com.ringov.yamblzweather.internet;

import com.ringov.yamblzweather.internet.content.WeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ringov on 14.07.17.
 */

public interface WeatherService {

    @GET("weather")
    Single<WeatherResponse> getWeather(@Query("id") int cityId);

}
