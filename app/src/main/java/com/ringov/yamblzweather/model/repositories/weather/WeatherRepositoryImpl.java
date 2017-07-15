package com.ringov.yamblzweather.model.repositories.weather;

import com.ringov.yamblzweather.model.db.Database;
import com.ringov.yamblzweather.model.db.data.DBWeather;
import com.ringov.yamblzweather.model.internet.APIFactory;
import com.ringov.yamblzweather.model.internet.Converter;
import com.ringov.yamblzweather.model.internet.WeatherService;
import com.ringov.yamblzweather.model.repositories.base.BaseRepositoryImpl;
import com.ringov.yamblzweather.viewmodel.data.WeatherInfo;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ringov on 12.07.17.
 */

@Singleton
public class WeatherRepositoryImpl extends BaseRepositoryImpl implements WeatherRepository {

    // todo move choosing city to the higher level
    private int cityId = 524901;

    @Override
    public Observable<WeatherInfo> updateWeatherInfo() {
        return getService().getWeather(cityId)
                .map(Converter::getWeatherInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<WeatherInfo> getLastWeatherInfo() {
        return Observable.just(Converter.getWeatherInfo(getDatabase().getWeather(cityId)));
    }

    private WeatherService getService() {
        return APIFactory.getRetrofitService(WeatherService.class);
    }

    private Database getDatabase() {
        return Database.getInstance();
    }
}
