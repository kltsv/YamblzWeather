package com.ringov.yamblzweather.model.weather;

import com.ringov.yamblzweather.model.base.BaseRepositoryImpl;
import com.ringov.yamblzweather.viewmodel.model.WeatherInfo;

import javax.inject.Singleton;

/**
 * Created by ringov on 12.07.17.
 */

@Singleton
public class WeatherRepositoryImpl extends BaseRepositoryImpl implements WeatherRepository {

    private int counter;
    private WeatherInfo[] mockInfos;

    public WeatherRepositoryImpl() {
        mockInfos = new WeatherInfo[3];
        mockInfos[0] = new WeatherInfo("тест 1");
        mockInfos[1] = new WeatherInfo("тест 2");
        mockInfos[2] = new WeatherInfo("тест 3");
    }

    @Override
    public WeatherInfo getWeatherInfo() {
        return mockInfos[(counter++) % mockInfos.length];
    }
}
