package com.ringov.yamblzweather.viewmodel.weather;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.weather.WeatherRepository;
import com.ringov.yamblzweather.viewmodel.base.BaseLiveData;
import com.ringov.yamblzweather.viewmodel.base.BaseViewModel;
import com.ringov.yamblzweather.viewmodel.model.WeatherInfo;

import javax.inject.Inject;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherViewModel extends BaseViewModel<BaseLiveData<WeatherInfo>, WeatherInfo> {

    @Inject
    WeatherRepository repository;

    public WeatherViewModel() {
        App.getComponent().inject(this);
    }

    public void getWeatherInfo() {
        WeatherInfo info = repository.getWeatherInfo();
        updateValue(info);
    }
}
