package com.ringov.yamblzweather.model.repository.weather;

import com.ringov.yamblzweather.model.repository.base.BaseRepository;
import com.ringov.yamblzweather.presentation.data.UIWeather;

import io.reactivex.Observable;

/**
 * Created by ringov on 12.07.17.
 */

public interface WeatherRepository extends BaseRepository {

    Observable<UIWeather> updateWeatherIfDataIsOld();

    Observable<UIWeather> updateWeather();

    Observable<UIWeather> getLastWeatherInfo();
}
