package com.ringov.yamblzweather.data.networking;

import com.ringov.yamblzweather.data.networking.entity.ForecastResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.ringov.yamblzweather.data.networking.APIContract.ENDPOINT_DAILY;
import static com.ringov.yamblzweather.data.networking.APIContract.ENDPOINT_FORECAST;
import static com.ringov.yamblzweather.data.networking.APIContract.PARAM_CITY_ID;

public interface WeatherAPI {

    @GET(ENDPOINT_FORECAST)
    Single<ForecastResponse> getForecast(@Query(PARAM_CITY_ID) int cityId);

    @GET(ENDPOINT_DAILY)
    Single<ForecastResponse> getDailyForecast(@Query(PARAM_CITY_ID) int cityId);
}
