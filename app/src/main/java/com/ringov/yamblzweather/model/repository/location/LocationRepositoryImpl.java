package com.ringov.yamblzweather.model.repository.location;

import com.ringov.yamblzweather.model.db.Database;
import com.ringov.yamblzweather.model.repository.base.BaseRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

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
        return Single.create(emitter -> {
            ArrayList<String> list = new ArrayList<>();

            list.add("Moscow");
            list.add("London");
            list.add("Paris");

            emitter.onSuccess(list);
        });
    }
}
