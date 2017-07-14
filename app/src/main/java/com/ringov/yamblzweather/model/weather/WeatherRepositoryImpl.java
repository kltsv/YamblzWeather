package com.ringov.yamblzweather.model.weather;

import com.ringov.yamblzweather.internet.APIFactory;
import com.ringov.yamblzweather.internet.Converter;
import com.ringov.yamblzweather.internet.WeatherService;
import com.ringov.yamblzweather.model.base.BaseRepositoryImpl;
import com.ringov.yamblzweather.viewmodel.base.BaseLiveData;
import com.ringov.yamblzweather.viewmodel.model.WeatherInfo;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ringov on 12.07.17.
 */

@Singleton
public class WeatherRepositoryImpl extends BaseRepositoryImpl implements WeatherRepository {

    @Override
    public Single<WeatherInfo> getWeatherInfo() {
        return getService().getWeather(524901)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(Converter::getWeatherInfo);
    }

    private WeatherService getService() {
        return APIFactory.getRetrofitService(WeatherService.class);
    }
}
