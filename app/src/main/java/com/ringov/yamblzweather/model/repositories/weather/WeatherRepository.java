package com.ringov.yamblzweather.model.repositories.weather;

import com.ringov.yamblzweather.model.repositories.base.BaseRepository;
import com.ringov.yamblzweather.viewmodel.model.WeatherInfo;

import io.reactivex.Observable;

/**
 * Created by ringov on 12.07.17.
 */

public interface WeatherRepository extends BaseRepository {
    Observable<WeatherInfo> getWeatherInfo();
}
