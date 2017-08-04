package com.ringov.yamblzweather.domain.weather;

import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;

import java.util.List;

import io.reactivex.Single;

public interface WeatherRepository {

    Single<List<UIWeatherList>> getForecast();

    Single<UIWeatherDetail> getWeather(long time);
}
