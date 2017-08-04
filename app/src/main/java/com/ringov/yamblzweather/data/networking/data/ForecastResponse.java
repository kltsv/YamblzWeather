package com.ringov.yamblzweather.data.networking.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {

    @SerializedName("list")
    private List<ResponseWeather> forecast;

    public List<ResponseWeather> getForecast() {
        return forecast;
    }

    public void setForecast(List<ResponseWeather> forecast) {
        this.forecast = forecast;
    }
}
