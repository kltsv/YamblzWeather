package com.ringov.yamblzweather.domain.repository.weather;

import android.support.annotation.AnyThread;
import android.support.annotation.WorkerThread;

import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.dao.WeatherDAO;
import com.ringov.yamblzweather.data.database.entity.DBWeather;
import com.ringov.yamblzweather.data.networking.WeatherAPI;
import com.ringov.yamblzweather.domain.repository.BaseRepository;
import com.ringov.yamblzweather.domain.Mapper;
import com.ringov.yamblzweather.domain.exceptions.EmptyForecastException;
import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import timber.log.Timber;

public class WeatherRepositoryImpl extends BaseRepository implements WeatherRepository {

    // Weather forecast will become outdated after 3 hours
    private static final long CACHE_INVALIDATION_TIME = TimeUnit.HOURS.toSeconds(12);

    private WeatherDAO weatherDAO;
    private FavoriteCityDAO favoriteCityDAO;
    private WeatherAPI weatherAPI;

    @Inject
    public WeatherRepositoryImpl(
            Scheduler schedulerUI, Scheduler schedulerIO, Scheduler schedulerComputation,
            WeatherDAO weatherDAO, FavoriteCityDAO favoriteCityDAO, WeatherAPI weatherAPI
    ) {
        super(schedulerUI, schedulerIO, schedulerComputation);
        this.weatherDAO = weatherDAO;
        this.favoriteCityDAO = favoriteCityDAO;
        this.weatherAPI = weatherAPI;
    }

    /**
     * Returns detailed forecast data for day from database
     */
    @Override
    public Single<UIWeatherDetail> getWeather(long time) {
        return getCurrentCityId()
                .flatMap(cityId -> Single.fromCallable(() -> weatherDAO.getWeather(cityId, time)))
                .map(Mapper::DBWeather_to_UIWeatherDetail)
                .subscribeOn(schedulerComputation)
                .observeOn(schedulerUI);
    }

    /**
     * Returns actual forecast data for 7 days.
     * <p>
     * First, tries to read forecast from cache, if cache has outdated forecast,
     * tries to fetch forecast data from API.
     */
    @Override
    public Single<List<UIWeatherList>> getForecast() {
        return getFromCache()
                .filter(weatherList -> weatherList.size() >= 7)
                .switchIfEmpty(getFromAPI().toMaybe())
                .observeOn(schedulerComputation)
                .toSingle()
                .map(Mapper::DBtoUI)
                .subscribeOn(schedulerComputation)
                .observeOn(schedulerUI);
    }

    @WorkerThread
    private Single<List<DBWeather>> getFromAPI() {
        return getCurrentCityId()
                .flatMap(cityId -> weatherAPI.getDailyForecast(cityId))
                .map(Mapper::APItoDB)
                .doOnSuccess(this::saveCache)
                .subscribeOn(schedulerIO)
                .observeOn(schedulerComputation);
    }

    @WorkerThread
    private Single<List<DBWeather>> getFromCache() {
        return getCurrentCityId()
                .flatMap(cityId -> Single.fromCallable(() -> readCache(cityId)))
                .doOnSubscribe(disposable -> clearOldCache())
                .observeOn(schedulerComputation)
                .subscribeOn(schedulerComputation);
    }

    @WorkerThread
    private Single<Integer> getCurrentCityId() {
        return Single.fromCallable(() -> favoriteCityDAO.getEnabled().getCity_id());
    }

    @AnyThread
    private long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    @WorkerThread
    private List<DBWeather> readCache(int cityId) {
        List<DBWeather> cache = weatherDAO.getForecast(cityId);
        Timber.d("Read cache size " + cache.size() + " for city id " + cityId);
        return cache;
    }

    @WorkerThread
    private void saveCache(List<DBWeather> weatherToCache) {
        if (weatherToCache.isEmpty()) {
            throw new EmptyForecastException("Trying to cache empty forecast list");
        } else {
            int cityId = weatherToCache.get(0).getCityId();
            // Remove all previous weather data for that city
            List<DBWeather> oldCache = weatherDAO.getByCiyId(cityId);
            weatherDAO.deleteAll(oldCache);
            weatherDAO.insertAll(weatherToCache);
            Timber.d("Delete cache size " + oldCache.size() + " for city id " + cityId);
            Timber.d("Saved cache size " + weatherToCache.size() + " for city id " + cityId);
        }
    }

    @WorkerThread
    private void clearOldCache() {
        // Delete forecast older, than specified time
        long currentTime = getCurrentTime();
        long diff = currentTime - CACHE_INVALIDATION_TIME;
        // Important to remember, that forecast for given day has time 12:00:00 GMT+0300
        List<DBWeather> oldCache = weatherDAO.getByTime(diff);
        Timber.d("Clear old cache size " + oldCache.size());
        if (!oldCache.isEmpty())
            weatherDAO.deleteAll(oldCache);
    }
}
