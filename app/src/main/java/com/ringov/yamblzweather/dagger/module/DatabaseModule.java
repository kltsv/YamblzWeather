package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.data.db.database.AppDatabase;
import com.ringov.yamblzweather.data.db.database.dao.CityDAO;
import com.ringov.yamblzweather.data.db.database.dao.WeatherDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private AppDatabase database;

    public DatabaseModule(AppDatabase database) {
        this.database = database;
    }

    @Provides
    @Singleton
    CityDAO provideCityDAO() {
        return database.cityDAO();
    }

    @Provides
    @Singleton
    WeatherDAO provideWeatherDAO() {
        return database.weatherDAO();
    }
}
