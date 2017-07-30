package com.ringov.yamblzweather.model.repository.location;

import com.ringov.yamblzweather.model.db.Database;
import com.ringov.yamblzweather.model.db.city.CityDatabaseCreator;
import com.ringov.yamblzweather.model.db.city.DBCity;
import com.ringov.yamblzweather.model.repository.base.BaseRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LocationRepositoryImpl extends BaseRepositoryImpl implements LocationRepository {

    // Limit suggestions count
    private static final int LIMIT = 5;

    // Returns city name
    @Override
    public Single<String> getLocation() {
        return Single.fromCallable(() -> {
            int cityId = Database.getInstance().getUserCityId();

            try {
                DBCity city = CityDatabaseCreator.getInstance().getDatabase().cityDAO().getById(cityId);
                return city.getCity_name();
            } catch (NullPointerException e) {
                Timber.e(e);
                return "Location unknown";
            }
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void changeLocation(String newValue) {
        Completable.fromCallable(() -> {
            try {
                DBCity city = CityDatabaseCreator.getInstance().getDatabase().cityDAO().getByName(newValue);

                Database.getInstance().putUserCity(city.getCity_id());
                return true;
            } catch (NullPointerException e) {
                Timber.e(e);
                return false;
            }
        })
                .subscribeOn(Schedulers.computation())
                .subscribe();
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
                    .getInstance().getDatabase().cityDAO().getSuggestions( "%" + suggestFrom + "%", LIMIT);
            for (DBCity city : cities)
                citiesNames.add(city.getCity_name());
        } catch (NullPointerException e) {
            Timber.e(e);
        }
        Timber.d("Found " + citiesNames.size() + " suggestion(s)");

        return citiesNames;
    }
}
