package com.ringov.yamblzweather.internet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ringov on 14.07.17.
 */

public interface WeatherAPI {

    @FormUrlEncoded
    @POST("weather")
    Call<BaseResponse> getWeather(@Field("id") int cityId);

}
