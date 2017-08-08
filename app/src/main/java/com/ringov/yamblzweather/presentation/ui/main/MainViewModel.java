package com.ringov.yamblzweather.presentation.ui.main;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.ringov.yamblzweather.domain.repository.favorite_city.FavoriteCityRepository;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAboutScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenForecastScreen;
import com.ringov.yamblzweather.presentation.base.BaseLiveData;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;
import com.ringov.yamblzweather.presentation.entity.UICityFavorite;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MainViewModel extends BaseViewModel {

    private BaseLiveData<List<UICityFavorite>> citiesData = new BaseLiveData<>();

    private Router router;
    private FavoriteCityRepository favoriteCityRepository;

    @Inject
    public MainViewModel(Router router, FavoriteCityRepository favoriteCityRepository) {
        this.router = router;
        this.favoriteCityRepository = favoriteCityRepository;

        loadFavoriteCities();
    }

    public void observe(
            LifecycleOwner owner,
            Observer<List<UICityFavorite>> citiesObserver
    ) {
        citiesData.observe(owner, citiesObserver);
    }

    // View callbacks
    public void onAboutNavigation() {
        router.execute(new CommandOpenAboutScreen());
    }

    public void onAddCityNavigation() {
        // TODO implement
    }

    public void onRemoveCityClick(UICityFavorite city) {
        // TODO implement
    }

    public void onFavoriteCityClick(UICityFavorite city) {
        if (city.isEnabled())
            return;

        disposables.add(
                favoriteCityRepository.select(city)
                        .subscribe(() -> {
                            loadFavoriteCities();
                            router.execute(new CommandOpenForecastScreen());
                        }));
    }

    // Private logic
    private void loadFavoriteCities() {
        disposables.add(
                favoriteCityRepository.getAll().subscribe(
                        uiCityFavorites -> citiesData.updateValue(uiCityFavorites)));
    }
}
