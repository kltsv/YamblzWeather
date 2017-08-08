package com.ringov.yamblzweather.domain.repository.favorite_city;

import android.support.annotation.WorkerThread;

import com.ringov.yamblzweather.data.database.dao.CityDAO;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.entity.DBCity;
import com.ringov.yamblzweather.data.database.entity.DBFavoriteCity;
import com.ringov.yamblzweather.domain.Mapper;
import com.ringov.yamblzweather.domain.repository.BaseRepository;
import com.ringov.yamblzweather.presentation.entity.UICity;
import com.ringov.yamblzweather.presentation.entity.UICityFavorite;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import timber.log.Timber;

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
    public Single<List<UICityFavorite>> getAll() {
        return getAllFavorite()
                .map(Mapper::DBtoUIFavoriteCities)
                .subscribeOn(schedulerComputation)
                .observeOn(schedulerUI);
    }

    @Override
    public Completable add(UICity city) {
        return getCityById(city.getCityId())
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

    @WorkerThread
    private Single<DBCity> getCityById(int id) {
        return Single.fromCallable(() -> cityDAO.getById(id));
    }

    @WorkerThread
    private Completable addFavoriteCity(DBFavoriteCity dbFavoriteCity) {
        return Completable.fromCallable(() -> {
            favoriteCityDAO.insert(dbFavoriteCity);
            Timber.d("Insert favorite city: " + dbFavoriteCity.getCity_name() + " is enabled " + dbFavoriteCity.isEnabled());
            DBFavoriteCity temp = favoriteCityDAO.getById(dbFavoriteCity.getCity_id());
            Timber.d("Insert favorite city, check if written successful " + temp.getCity_name() + " is enabled" + temp.isEnabled());
            return true;
        });
    }

    @WorkerThread
    private Completable removeFavoriteCity(DBFavoriteCity dbFavoriteCity) {
        return Completable.fromCallable(() -> {
            favoriteCityDAO.delete(dbFavoriteCity);
            return true;
        });
    }
}
