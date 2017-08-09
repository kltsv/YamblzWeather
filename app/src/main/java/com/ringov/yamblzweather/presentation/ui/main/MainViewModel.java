package com.ringov.yamblzweather.presentation.ui.main;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.ringov.yamblzweather.domain.repository.favorite_city.FavoriteCityRepository;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.navigation.commands.CommandCloseDrawer;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAboutScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAddCityScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenForecastScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenSettingsScreen;
import com.ringov.yamblzweather.presentation.base.BaseLiveData;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;
import com.ringov.yamblzweather.presentation.entity.UICityFavorite;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends BaseViewModel {

    private BaseLiveData<List<UICityFavorite>> citiesData = new BaseLiveData<>();

    private Router router;
    private FavoriteCityRepository favoriteCityRepository;

    @Inject
    public MainViewModel(Router router, FavoriteCityRepository favoriteCityRepository) {
        this.router = router;
        this.favoriteCityRepository = favoriteCityRepository;

        disposables.add(
                favoriteCityRepository
                        .getAll()
                        .subscribe(uiCityFavorites -> citiesData.updateValue(uiCityFavorites))
        );
    }

    public void observe(
            LifecycleOwner owner,
            Observer<List<UICityFavorite>> citiesObserver
    ) {
        citiesData.observe(owner, citiesObserver);
    }

    // View callbacks
    public void onSettingsNavigation() {
        router.execute(new CommandOpenSettingsScreen());
    }

    public void onAboutNavigation() {
        router.execute(new CommandOpenAboutScreen());
    }

    public void onAddCityNavigation() {
        router.execute(new CommandOpenAddCityScreen());
    }

    public void onRemoveCityClick(UICityFavorite city) {
        disposables.add(
                favoriteCityRepository.remove(city).subscribe(() -> {
                    // If user removes currently selected city, update forecast screen
                    if (city.isEnabled()) showWeatherForNewCity();
                }));
    }

    public void onFavoriteCityClick(UICityFavorite city) {
        if (city.isEnabled()) {
            // If user clicks on enabled city there is no reason re-select in again
            // so just close nav drawer and let user watch forecast.
            router.execute(new CommandCloseDrawer());
        } else {
            disposables.add(
                    favoriteCityRepository.select(city).subscribe(this::showWeatherForNewCity));
        }
    }

    // Private logic
    private void showWeatherForNewCity() {
        router.execute(new CommandOpenForecastScreen());
    }
}
