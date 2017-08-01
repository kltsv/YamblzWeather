package com.ringov.yamblzweather.domain.weather;

import com.ringov.yamblzweather.presentation.data.UIWeather;

import io.reactivex.Observable;

public interface WeatherRepository {

    Observable<UIWeather> updateWeatherIfDataIsOld();

    Observable<UIWeather> updateWeather();

    Observable<UIWeather> getLastWeatherInfo();
}
