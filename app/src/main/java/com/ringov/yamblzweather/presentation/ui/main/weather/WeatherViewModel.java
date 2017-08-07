package com.ringov.yamblzweather.presentation.ui.main.weather;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.navigation.commands.CommandOpenWeatherDetails;
import com.ringov.yamblzweather.presentation.base.BaseLiveData;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;

import java.util.List;

import javax.inject.Inject;

public class WeatherViewModel extends BaseViewModel {

    // TODO show selected City

    private BaseLiveData<Boolean> loadingData = new BaseLiveData<>();
    private BaseLiveData<List<UIWeatherList>> weatherData = new BaseLiveData<>();
    private BaseLiveData<Throwable> errorData = new BaseLiveData<>();
    //private BaseLiveData<String> cityData = new BaseLiveData<>();

    private Router router;
    private WeatherRepository weatherRepository;

    @Inject
    public WeatherViewModel(Router router, WeatherRepository weatherRepo) {
        this.router = router;
        this.weatherRepository = weatherRepo;

        disposables.add(
                weatherRepository
                        .getForecast()
                        .doOnSubscribe(disposable -> loadingData.updateValue(true))
                        .doFinally(() -> loadingData.updateValue(false))
                        .subscribe(
                                uiWeather -> weatherData.updateValue(uiWeather),
                                throwable -> errorData.updateValue(throwable)
                        )
        );
    }

    void observe(
            LifecycleOwner owner,
            Observer<Boolean> loadingObserver,
            Observer<List<UIWeatherList>> weatherObserver,
            Observer<Throwable> errorObserver
            //Observer<String> cityObserver
    ) {
        loadingData.observe(owner, loadingObserver);
        weatherData.observe(owner, weatherObserver);
        errorData.observe(owner, errorObserver);
        //cityData.observe(owner, cityObserver);
    }

    // View callbacks
    void onRefresh() {
        // TODO
    }

    void openWeatherDetails(UIWeatherList weather) {
        router.execute(new CommandOpenWeatherDetails(weather.getTime()));
    }
}
