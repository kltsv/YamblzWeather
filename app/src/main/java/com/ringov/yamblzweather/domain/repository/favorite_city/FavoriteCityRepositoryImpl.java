package com.ringov.yamblzweather.domain.repository.favorite_city;

import android.support.annotation.WorkerThread;

import com.ringov.yamblzweather.data.database.dao.CityDAO;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.entity.DBCity;
import com.ringov.yamblzweather.data.database.entity.DBFavoriteCity;
import com.ringov.yamblzweather.domain.Mapper;
import com.ringov.yamblzweather.domain.repository.BaseRepository;
import com.ringov.yamblzweather.presentation.entity.UICityFavorite;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class FavoriteCityRepositoryImpl extends BaseRepository implements FavoriteCityRepository {

    private FavoriteCityDAO favoriteCityDAO;
    private CityDAO cityDAO;

    @Inject
    public FavoriteCityRepositoryImpl(
            Scheduler schedulerUI, Scheduler schedulerIO, Scheduler schedulerComputation,
            FavoriteCityDAO favoriteCityDAO, CityDAO cityDAO
    ) {
        super(schedulerUI, schedulerIO, schedulerComputation);
        this.favoriteCityDAO = favoriteCityDAO;
        this.cityDAO = cityDAO;
    }

    @Override
    public Completable select(UICityFavorite city) {
        return getEnabledFavoriteCity()
                .map(currentEnabled -> {
                    currentEnabled.setEnabled(false);
                    return currentEnabled;
                })
                .flatMapCompletable(this::addFavoriteCity)
                .andThen(getFavoriteById(city.getCityId()))
                .map(newEnabled -> {
                    newEnabled.setEnabled(true);
                    return newEnabled;
                })
                .flatMapCompletable(this::addFavoriteCity)
                .subscribeOn(schedulerComputation)
                .observeOn(schedulerUI);
    }

    @Override
    public Single<String> getSelectedCityName() {
        return getEnabledFavoriteCity()
                .map(DBFavoriteCity::getCity_name)
                .subscribeOn(schedulerComputation)
                .observeOn(schedulerUI);
    }

    @Override
    public Single<List<UICityFavorite>> getAll() {
        return getAllFavorite()
                .map(Mapper::DBtoUIFavoriteCities)
                .subscribeOn(schedulerComputation)
                .observeOn(schedulerUI);
    }

    @Override
    public Completable add(String cityName) {
        return getCityByName(cityName)
                .map(Mapper::DBCityToDBFavoriteCity)
                .flatMapCompletable(this::addFavoriteCity)
                .subscribeOn(schedulerComputation)
                .observeOn(schedulerUI);
    }

    @Override
    public Completable remove(UICityFavorite city) {
        return getFavoriteById(city.getCityId())
                .flatMapCompletable(this::removeFavoriteCity)
                .subscribeOn(schedulerComputation)
                .observeOn(schedulerUI);
    }

    // DB related methods
    @WorkerThread
    private Single<DBFavoriteCity> getEnabledFavoriteCity() {
        return Single.fromCallable(() -> favoriteCityDAO.getEnabled());
    }

    @WorkerThread
    private Single<List<DBFavoriteCity>> getAllFavorite() {
        return Single.fromCallable(() -> favoriteCityDAO.getAll());
    }

    @WorkerThread
    private Single<DBFavoriteCity> getFavoriteById(int id) {
        return Single.fromCallable(() -> favoriteCityDAO.getById(id));
    }

    // Important! This method works with regular cities table, not with favorite cities
    @WorkerThread
    private Single<DBCity> getCityByName(String name) {
        return Single.fromCallable(() -> cityDAO.getByName(name));
    }

    @WorkerThread
    private Completable addFavoriteCity(DBFavoriteCity dbFavoriteCity) {
        return Completable.fromCallable(() -> {
            favoriteCityDAO.insert(dbFavoriteCity);
            return true;
        });
    }

    @WorkerThread
    private Completable removeFavoriteCity(DBFavoriteCity dbFavoriteCity) {
        return Completable.fromCallable(() -> {
            favoriteCityDAO.delete(dbFavoriteCity);
            // Check, if was enabled city, so select something instead of it
            if (dbFavoriteCity.isEnabled()) {
                // Here can be an ArrayOutOfBoundException, if we deleted last FavoriteCity in db,
                // but this handles in ViewModel - there is no way to delete favorite city
                // if there is only one favorite city.
                DBFavoriteCity newEnabled = favoriteCityDAO.getAll().get(0);
                newEnabled.setEnabled(true);
                favoriteCityDAO.insert(newEnabled);
            }
            return true;
        });
    }
}
