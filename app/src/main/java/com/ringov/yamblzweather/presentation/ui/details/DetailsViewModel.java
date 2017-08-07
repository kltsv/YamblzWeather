package com.ringov.yamblzweather.presentation.ui.details;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.presentation.base.BaseLiveData;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;
import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;

import javax.inject.Inject;

public class DetailsViewModel extends BaseViewModel {

    private BaseLiveData<Boolean> loadingData = new BaseLiveData<>();
    private BaseLiveData<UIWeatherDetail> weatherData = new BaseLiveData<>();
    private BaseLiveData<Throwable> errorData = new BaseLiveData<>();

    private WeatherRepository weatherRepository;

    @Inject
    DetailsViewModel(WeatherRepository repository) {
        this.weatherRepository = repository;
    }

    void observe(
            LifecycleOwner owner,
            Observer<Boolean> loadingObserver,
            Observer<Throwable> errorObserver,
            Observer<UIWeatherDetail> weatherObserver
    ) {
        loadingData.observe(owner, loadingObserver);
        weatherData.observe(owner, weatherObserver);
        errorData.observe(owner, errorObserver);
    }

    // View callbacks
    void showWeatherFor(long time) {
        disposables.add(
                weatherRepository
                        .getWeather(time)
                        .doOnSubscribe(d -> loadingData.updateValue(true))
                        .doFinally(() -> loadingData.updateValue(false))
                        .subscribe(
                                uiWeatherDetail -> weatherData.updateValue(uiWeatherDetail),
                                throwable -> errorData.updateValue(throwable)
                        )
        );
    }
}
