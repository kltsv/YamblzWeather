package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.data.database.AppDatabase;
import com.ringov.yamblzweather.data.database.dao.CityDAO;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.dao.WeatherDAO;

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
    FavoriteCityDAO provideFavoriteCityDAO() {
        return database.favoriteCityDAO();
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
