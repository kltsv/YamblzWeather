package com.ringov.yamblzweather.presentation.ui.main.forecast;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.ringov.yamblzweather.domain.repository.favorite_city.FavoriteCityRepository;
import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.navigation.commands.CommandOpenWeatherDetails;
import com.ringov.yamblzweather.presentation.base.BaseLiveData;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;

import java.util.List;

import javax.inject.Inject;

public class ForecastViewModel extends BaseViewModel {

    // TODO show selected City

    private BaseLiveData<Boolean> loadingData = new BaseLiveData<>();
    private BaseLiveData<List<UIWeatherList>> weatherData = new BaseLiveData<>();
    private BaseLiveData<Throwable> errorData = new BaseLiveData<>();
    private BaseLiveData<String> cityData = new BaseLiveData<>();

    private Router router;
    private WeatherRepository weatherRepository;
    private FavoriteCityRepository favoriteCityRepository;

    @Inject
    public ForecastViewModel(
            Router router,
            WeatherRepository weatherRepo,
            FavoriteCityRepository favoriteCityRepository
    ) {
        this.router = router;
        this.weatherRepository = weatherRepo;
        this.favoriteCityRepository = favoriteCityRepository;

        loadWeather(false);
        loadEnabledCity();
    }

    void observe(
            LifecycleOwner owner,
            Observer<Boolean> loadingObserver,
            Observer<List<UIWeatherList>> weatherObserver,
            Observer<Throwable> errorObserver,
            Observer<String> cityObserver
    ) {
        loadingData.observe(owner, loadingObserver);
        weatherData.observe(owner, weatherObserver);
        errorData.observe(owner, errorObserver);
        cityData.observe(owner, cityObserver);
    }

    // View callbacks
    void onRefresh() {
        loadWeather(true);
    }

    void openWeatherDetails(UIWeatherList weather) {
        router.execute(new CommandOpenWeatherDetails(weather.getTime()));
    }

    // Private logic
    private void loadWeather(boolean forceRefresh) {
        disposables.add(
                weatherRepository
                        .getForecast(forceRefresh)
                        .doOnSubscribe(disposable -> loadingData.updateValue(true))
                        .doFinally(() -> loadingData.updateValue(false))
                        .subscribe(
                                uiWeather -> weatherData.updateValue(uiWeather),
                                throwable -> errorData.updateValue(throwable)
                        )
        );
    }

    private void loadEnabledCity() {
        disposables.add(
                favoriteCityRepository
                        .getSelectedCityName()
                        .subscribe(cityFavorite -> cityData.updateValue(cityFavorite))
        );
    }
}
