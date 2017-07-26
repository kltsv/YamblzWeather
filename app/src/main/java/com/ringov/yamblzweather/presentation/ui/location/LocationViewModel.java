package com.ringov.yamblzweather.presentation.ui.location;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.repositories.location.LocationRepository;
import com.ringov.yamblzweather.presentation.base.BaseLiveData;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Ivan on 26.07.2017.
 */

public class LocationViewModel extends BaseViewModel {

    private BaseLiveData<Boolean> loadingData = new BaseLiveData<>();
    private BaseLiveData<List<String>> suggestionsData = new BaseLiveData<>();
    private BaseLiveData<Throwable> errorData = new BaseLiveData<>();

    @Inject
    LocationRepository repository;

    public LocationViewModel() {
        App.getComponent().inject(this);
    }

    void observe(
            LifecycleOwner owner,
            Observer<Boolean> loadingObserver,
            Observer<List<String>> suggestionsObserver,
            Observer<Throwable> errorObserver
    ) {
        loadingData.observe(owner, loadingObserver);
        suggestionsData.observe(owner, suggestionsObserver);
        errorData.observe(owner, errorObserver);
    }

    // Callbacks from view
    void onInputChanges(String input) {
        Timber.d(input);

        disposables.add(
                repository
                        .getSuggestions(input)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> loadingData.updateValue(true))
                        .doFinally(() -> loadingData.updateValue(false))
                        .subscribe(
                                strings -> suggestionsData.updateValue(strings),
                                throwable -> errorData.updateValue(throwable)
                        )
        );
    }
}
