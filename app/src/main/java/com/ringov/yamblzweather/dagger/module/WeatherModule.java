package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.domain.weather.WeatherRepository;
import com.ringov.yamblzweather.domain.weather.WeatherRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository() {
        return new WeatherRepositoryImpl();
    }
}
