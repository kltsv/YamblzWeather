package com.ringov.yamblzweather.model.repository.weather;

import com.ringov.yamblzweather.model.Converter;
import com.ringov.yamblzweather.model.db.Database;
import com.ringov.yamblzweather.model.db.data.DBWeather;
import com.ringov.yamblzweather.model.networking.APIFactory;
import com.ringov.yamblzweather.model.networking.WeatherService;
import com.ringov.yamblzweather.model.repository.base.BaseRepositoryImpl;
import com.ringov.yamblzweather.presentation.data.UIWeather;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ringov on 12.07.17.
 */

@Singleton
public class WeatherRepositoryImpl extends BaseRepositoryImpl implements WeatherRepository {

    private static final int REQUEST_FREQUENCY = 2; // not frequently than once in two minutes


    private Observable<DBWeather> getCachedWeather() {
        return Observable.just(getDatabase().loadWeather());
    }

    @Override
    public Observable<UIWeather> updateWeatherIfDataIsOld() {
        return getCachedWeather()
                .flatMap(dbWeather -> {
                    // if last value was cached less than 2 minutes ago, do not send request to the network
                    return System.currentTimeMillis() - dbWeather.getTime() < TimeUnit.MINUTES.toMillis(REQUEST_FREQUENCY) ?
                            Observable.just(dbWeather) :
                            updateWeatherAndCache();
                })
                .map(Converter::getUIWeather)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<UIWeather> updateWeather() {
        return updateWeatherAndCache()
                .map(Converter::getUIWeather)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    private Observable<DBWeather> updateWeatherAndCache() {
        return getService().getWeather(Database.getInstance().getUserCity())
                .map(Converter::getDBWeather)
                .doOnNext(Database.getInstance()::saveWeather);
    }

    @Override
    public Observable<UIWeather> getLastWeatherInfo() {
        return getCachedWeather().map(Converter::getUIWeather);
    }

    private WeatherService getService() {
        return APIFactory.getRetrofitService(WeatherService.class);
    }

    private Database getDatabase() {
        return Database.getInstance();
    }
}
