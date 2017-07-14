package com.ringov.yamblzweather.model.weather;

import com.ringov.yamblzweather.internet.APIFactory;
import com.ringov.yamblzweather.internet.WeatherService;
import com.ringov.yamblzweather.model.base.BaseRepositoryImpl;
import com.ringov.yamblzweather.viewmodel.base.BaseLiveData;
import com.ringov.yamblzweather.viewmodel.model.WeatherInfo;

import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ringov on 12.07.17.
 */

@Singleton
public class WeatherRepositoryImpl extends BaseRepositoryImpl implements WeatherRepository {

    private BaseLiveData<WeatherInfo> liveData;

    public WeatherRepositoryImpl() {
        liveData = new BaseLiveData<>();
    }

    @Override
    public BaseLiveData<WeatherInfo> getWeatherInfo() {
        getService().getWeather(524901)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    liveData.updateValue(new WeatherInfo(response.getName()));
                }, throwable -> {
                });

        return liveData;
    }

    private WeatherService getService() {
        return APIFactory.getRetrofitService(WeatherService.class);
    }
}
