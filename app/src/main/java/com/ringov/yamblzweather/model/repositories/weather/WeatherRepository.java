package com.ringov.yamblzweather.model.repositories.weather;

import com.ringov.yamblzweather.model.repositories.base.BaseRepository;
import com.ringov.yamblzweather.viewmodel.data.UIWeather;

import io.reactivex.Observable;

/**
 * Created by ringov on 12.07.17.
 */

public interface WeatherRepository extends BaseRepository {
    Observable<UIWeather> updateWeatherIfDataIsOld();

    Observable<UIWeather> updateWeather();

    Observable<UIWeather> getLastWeatherInfo();
}
