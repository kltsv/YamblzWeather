package com.ringov.yamblzweather.presentation.ui.main.add_city;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.ringov.yamblzweather.domain.repository.city_suggestions.CitySuggestionsRepository;
import com.ringov.yamblzweather.domain.repository.favorite_city.FavoriteCityRepository;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.navigation.commands.CommandOpenForecastScreen;
import com.ringov.yamblzweather.presentation.base.BaseLiveData;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class AddCityViewModel extends BaseViewModel {

    private BaseLiveData<Boolean> loadingData = new BaseLiveData<>();
    private BaseLiveData<List<String>> suggestionsData = new BaseLiveData<>();

    private Router router;
    private CitySuggestionsRepository citySuggestionsRepository;
    private FavoriteCityRepository favoriteCityRepository;

    @Inject
    public AddCityViewModel(
            Router router,
            CitySuggestionsRepository citySuggestionsRepository,
            FavoriteCityRepository favoriteCityRepository
    ) {
        this.router = router;
        this.citySuggestionsRepository = citySuggestionsRepository;
        this.favoriteCityRepository = favoriteCityRepository;
    }

    public void observe(
            LifecycleOwner owner,
            Observer<Boolean> loadingObserver,
            Observer<List<String>> suggestionsObserver
    ) {
        loadingData.observe(owner, loadingObserver);
        suggestionsData.observe(owner, suggestionsObserver);
    }

    // Callbacks from view
    public void onCitySelected(String city) {
        disposables.add(
                favoriteCityRepository.add(city)
                        .subscribe(() -> router.execute(new CommandOpenForecastScreen()))
        );
    }

    public void onInputChanges(String input) {
        disposables.add(
                citySuggestionsRepository
                        .getSuggestions(input)
                        .doOnSubscribe(disposable -> loadingData.updateValue(true))
                        .doFinally(() -> loadingData.updateValue(false))
                        .subscribe(strings -> suggestionsData.updateValue(strings))
        );
    }
}
