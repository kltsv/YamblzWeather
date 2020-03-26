package com.ringov.yamblzweather.data.networking;

import com.ringov.yamblzweather.data.networking.entity.ForecastResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.ringov.yamblzweather.data.networking.APIContract.ENDPOINT_DAILY;
import static com.ringov.yamblzweather.data.networking.APIContract.PARAM_CITY_ID;
import static com.ringov.yamblzweather.data.networking.APIContract.PARAM_COUNT;

public interface WeatherAPI {

    @GET(ENDPOINT_DAILY)
    Single<ForecastResponse> getDailyForecast(
            @Query(PARAM_CITY_ID) int cityId, @Query(PARAM_COUNT) int count);
}
