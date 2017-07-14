package com.ringov.yamblzweather.model.weather;

import com.ringov.yamblzweather.model.base.BaseRepository;
import com.ringov.yamblzweather.viewmodel.base.BaseLiveData;
import com.ringov.yamblzweather.viewmodel.model.WeatherInfo;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by ringov on 12.07.17.
 */

public interface WeatherRepository extends BaseRepository {
    Observable<WeatherInfo> getWeatherInfo();
}
