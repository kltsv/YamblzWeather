package com.ringov.yamblzweather.presenter.weather;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.repositories.weather.WeatherRepository;
import com.ringov.yamblzweather.presenter.base.BaseLiveData;
import com.ringov.yamblzweather.presenter.base.BaseViewModel;
import com.ringov.yamblzweather.presenter.model.WeatherInfo;

import javax.inject.Inject;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherViewModel extends BaseViewModel<BaseLiveData<WeatherInfo>, WeatherInfo> {

    @Inject
    WeatherRepository repository;

    public WeatherViewModel() {
        App.getComponent().inject(this);
        addDisposable(repository.getWeatherInfo()
                .subscribe(this::updateData, this::handleError));
    }

    private void updateData(WeatherInfo weather) {
        getLiveData().updateValue(weather);
    }
}
