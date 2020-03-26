package com.ringov.yamblzweather.data.networking.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {

    @SerializedName("list")
    private List<ResponseWeather> forecast;
    @SerializedName("city")
    private City city;

    public List<ResponseWeather> getForecast() {
        return forecast;
    }

    public void setForecast(List<ResponseWeather> forecast) {
        this.forecast = forecast;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
