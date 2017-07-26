package com.ringov.yamblzweather.model.repositories.location;

import com.ringov.yamblzweather.model.repositories.base.BaseRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class LocationRepositoryImpl extends BaseRepositoryImpl implements LocationRepository {

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
