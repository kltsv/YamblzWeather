package com.ringov.yamblzweather.domain.repository.weather;

import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;

import java.util.List;

import io.reactivex.Single;

public interface WeatherRepository {

    Single<List<UIWeatherList>> getForecast(boolean forceRefresh);

    Single<UIWeatherDetail> getWeather(long time);
}
