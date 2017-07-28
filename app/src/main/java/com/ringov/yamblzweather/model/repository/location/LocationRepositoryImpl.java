package com.ringov.yamblzweather.model.repository.location;

import com.ringov.yamblzweather.model.db.Database;
import com.ringov.yamblzweather.model.db.city.CityDatabaseCreator;
import com.ringov.yamblzweather.model.db.city.DBCity;
import com.ringov.yamblzweather.model.repository.base.BaseRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
        return Single
                .fromCallable(() -> readFromDB(input))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<String> readFromDB(String suggestFrom) {
        List<String> citiesNames = new ArrayList<>();

        try {
            List<DBCity> cities = CityDatabaseCreator
                    .getInstance().getDatabase().cityDAO().getSuggestions(suggestFrom + "%");
            for (DBCity city : cities)
                citiesNames.add(city.getCity_name());
        } catch (NullPointerException e) {
            Timber.e(e);
        }

        return citiesNames;
    }
}
