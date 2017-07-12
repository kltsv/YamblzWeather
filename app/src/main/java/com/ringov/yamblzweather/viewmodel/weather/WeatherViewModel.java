package com.ringov.yamblzweather.viewmodel.weather;

import android.arch.lifecycle.LiveData;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.weather.WeatherRepository;
import com.ringov.yamblzweather.viewmodel.base.BaseViewModel;
import com.ringov.yamblzweather.viewmodel.model.WeatherInfo;

import javax.inject.Inject;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherViewModel extends BaseViewModel {

    private LiveData<WeatherInfo> weatherInfo;

    @Inject
    WeatherRepository repository;

    @Inject
    WeatherViewModel() {
        App.getComponent().inject(this);
    }

    public LiveData<WeatherInfo> getWeatherInfo() {
        return weatherInfo;
    }
}
