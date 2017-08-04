package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.data.db.database.dao.WeatherDAO;
import com.ringov.yamblzweather.domain.weather.WeatherRepository;
import com.ringov.yamblzweather.domain.weather.WeatherRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = DatabaseModule.class)
public class WeatherModule {

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(WeatherDAO weatherDAO) {
        return new WeatherRepositoryImpl(weatherDAO);
    }
}
