package com.ringov.yamblzweather.presentation.ui.details;

import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;

import javax.inject.Inject;

public class DetailsViewModel extends BaseViewModel {

    private WeatherRepository weatherRepository;

    @Inject
    public DetailsViewModel(WeatherRepository repository) {
        this.weatherRepository = repository;
    }

    // View callbacks
    void showWeatherFor(long time, int cityId) {
        // TODO implement
    }
}
