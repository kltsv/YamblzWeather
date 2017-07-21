package com.ringov.yamblzweather.di.weather;

import android.support.annotation.NonNull;

import com.ringov.yamblzweather.model.repositories.weather.WeatherRepository;
import com.ringov.yamblzweather.model.repositories.weather.WeatherRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ringov on 12.07.17.
 */

@Module
public class WeatherModule {

    @Provides
    @NonNull
    @Singleton
    WeatherRepository provideWeatherRepository() {
        return new WeatherRepositoryImpl();
    }
}
