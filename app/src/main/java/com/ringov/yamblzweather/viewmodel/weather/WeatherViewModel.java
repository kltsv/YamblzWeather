package com.ringov.yamblzweather.viewmodel.weather;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.repositories.weather.WeatherRepository;
import com.ringov.yamblzweather.viewmodel.base.BaseLiveData;
import com.ringov.yamblzweather.viewmodel.base.BaseViewModel;
import com.ringov.yamblzweather.viewmodel.data.WeatherInfo;

import javax.inject.Inject;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherViewModel extends BaseViewModel<BaseLiveData<WeatherInfo>, WeatherInfo> {

    @Inject
    WeatherRepository repository;

    public WeatherViewModel() {
        App.getComponent().inject(this);
        addDisposable(repository.getLastWeatherInfo()
                .subscribe(this::updateData, this::handleError));
    }

    private void updateData(WeatherInfo weather) {
        getLiveData().updateValue(weather);
    }

    public void onRefresh() {
        addDisposable(repository.updateWeatherInfo()
                .subscribe(this::updateData, this::handleError));
    }
}
