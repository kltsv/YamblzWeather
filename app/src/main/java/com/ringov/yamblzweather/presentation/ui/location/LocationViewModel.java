package com.ringov.yamblzweather.presentation.ui.location;

import android.util.Log;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.repositories.location.LocationRepository;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ivan on 26.07.2017.
 */

public class LocationViewModel extends BaseViewModel<List<String>, LocationStateData> {

    @Inject
    LocationRepository repository;

    public LocationViewModel() {
        App.getComponent().inject(this);
    }

    public void onInputChanges(String input) {
        Log.d("LocationViewModel", input);

        addDisposable(
                repository
                        .getSuggestions(input)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateValue)
        );
    }

    @Override
    protected LocationStateData getInitialState() {
        return new LocationStateData();
    }
}
