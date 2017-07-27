package com.ringov.yamblzweather.model.repository.location;

import com.ringov.yamblzweather.model.db.Database;
import com.ringov.yamblzweather.model.repository.base.BaseRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocationRepositoryImpl extends BaseRepositoryImpl implements LocationRepository {

    @Override
    public String getLocation() {
        return Database.getInstance().getUserCity();
    }

    @Override
    public void changeLocation(String newValue) {
        Database.getInstance().putUserCity(newValue);
    }

    @Override
    public Single<List<String>> getSuggestions(String input) {
        List<String> list = new ArrayList<>();

        list.add("Moscow");
        list.add("London");
        list.add("Paris");

        return Single
                .just(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
