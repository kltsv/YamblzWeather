package com.ringov.yamblzweather.viewmodel.weather;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.weather.WeatherRepository;
import com.ringov.yamblzweather.viewmodel.base.BaseLiveData;
import com.ringov.yamblzweather.viewmodel.base.BaseViewModel;
import com.ringov.yamblzweather.viewmodel.model.WeatherInfo;

import javax.inject.Inject;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherViewModel extends BaseViewModel<BaseLiveData<WeatherInfo>, WeatherInfo> implements LifecycleOwner {

    @Inject
    WeatherRepository repository;

    public WeatherViewModel() {
        App.getComponent().inject(this);
    }

    public void getWeatherInfo() {
        // temporary solution
        repository.getWeatherInfo()
                .observe(this, weatherInfo -> updateValue(weatherInfo));
    }

    @Override
    public Lifecycle getLifecycle() {
        return new Lifecycle() {
            @Override
            public void addObserver(LifecycleObserver observer) {

            }

            @Override
            public void removeObserver(LifecycleObserver observer) {

            }

            @Override
            public State getCurrentState() {
                return State.RESUMED;
            }
        };
    }
}
