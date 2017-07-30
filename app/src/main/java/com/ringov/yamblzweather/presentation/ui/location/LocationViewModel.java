package com.ringov.yamblzweather.presentation.ui.location;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.repository.location.LocationRepository;
import com.ringov.yamblzweather.presentation.base.BaseLiveData;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ivan on 26.07.2017.
 */

public class LocationViewModel extends BaseViewModel {

    private BaseLiveData<Boolean> loadingData = new BaseLiveData<>();
    private BaseLiveData<List<String>> suggestionsData = new BaseLiveData<>();
    private BaseLiveData<Throwable> errorData = new BaseLiveData<>();
    private BaseLiveData<String> cityData = new BaseLiveData<>();

    @Inject
    LocationRepository repository;

    public LocationViewModel() {
        App.getComponent().inject(this);

        disposables.add(
                repository
                        .getLocation()
                        .subscribe(s -> cityData.updateValue(s), e -> errorData.updateValue(e))
        );
    }

    void observe(
            LifecycleOwner owner,
            Observer<Boolean> loadingObserver,
            Observer<List<String>> suggestionsObserver,
            Observer<Throwable> errorObserver,
            Observer<String> cityObserver
    ) {
        loadingData.observe(owner, loadingObserver);
        suggestionsData.observe(owner, suggestionsObserver);
        errorData.observe(owner, errorObserver);
        cityData.observe(owner, cityObserver);
    }

    // Callbacks from view
    void onCitySelected(String city) {
        repository.changeLocation(city);
    }

    void onInputChanges(String input) {
        disposables.add(
                repository
                        .getSuggestions(input)
                        .doOnSubscribe(disposable -> loadingData.updateValue(true))
                        .doFinally(() -> loadingData.updateValue(false))
                        .subscribe(
                                strings -> suggestionsData.updateValue(strings),
                                throwable -> errorData.updateValue(throwable)
                        )
        );
    }
}
