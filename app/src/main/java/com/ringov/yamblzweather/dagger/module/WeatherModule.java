package com.ringov.yamblzweather.dagger.module;

import android.support.annotation.NonNull;

import com.ringov.yamblzweather.model.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.model.repository.weather.WeatherRepositoryImpl;

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
