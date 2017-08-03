package com.ringov.yamblzweather.domain.weather;

import com.ringov.yamblzweather.data.Converter;
import com.ringov.yamblzweather.data.db.DatabaseLegacy;
import com.ringov.yamblzweather.data.db.data.DBWeather;
import com.ringov.yamblzweather.data.networking.APIFactory;
import com.ringov.yamblzweather.data.networking.WeatherAPI;
import com.ringov.yamblzweather.presentation.data.UIWeather;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class WeatherRepositoryImpl implements WeatherRepository {

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
        return getService().getWeather(DatabaseLegacy.getInstance().getUserCityId())
                .map(Converter::getDBWeather)
                .doOnNext(DatabaseLegacy.getInstance()::saveWeather);
    }

    @Override
    public Observable<UIWeather> getLastWeatherInfo() {
        return getCachedWeather().map(Converter::getUIWeather);
    }

    private WeatherAPI getService() {
        return APIFactory.getRetrofitService(WeatherAPI.class);
    }

    private DatabaseLegacy getDatabase() {
        return DatabaseLegacy.getInstance();
    }
}
