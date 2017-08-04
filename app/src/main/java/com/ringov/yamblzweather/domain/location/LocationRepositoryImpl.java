package com.ringov.yamblzweather.domain.location;

import com.ringov.yamblzweather.data.db.DatabaseLegacy;
import com.ringov.yamblzweather.data.db.database.dao.CityDAO;
import com.ringov.yamblzweather.data.db.database.entity.DBCity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LocationRepositoryImpl implements LocationRepository {

    // Limit suggestions count
    private static final int LIMIT = 5;

    private CityDAO cityDAO;

    @Inject
    public LocationRepositoryImpl(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    // Returns city name
    @Override
    public Single<String> getLocation() {
        return Single.fromCallable(() -> {
            int cityId = DatabaseLegacy.getInstance().getUserCityId();

            try {
                DBCity city = cityDAO.getById(cityId);
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
                DBCity city = cityDAO.getByName(newValue);

                DatabaseLegacy.getInstance().putUserCity(city.getCity_id());
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
            List<DBCity> cities = cityDAO.getSuggestions( "%" + suggestFrom + "%", LIMIT);
            for (DBCity city : cities)
                citiesNames.add(city.getCity_name());
        } catch (NullPointerException e) {
            Timber.e(e);
        }
        Timber.d("Found " + citiesNames.size() + " suggestion(s)");

        return citiesNames;
    }
}
