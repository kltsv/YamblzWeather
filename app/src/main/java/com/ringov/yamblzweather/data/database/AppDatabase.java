package com.ringov.yamblzweather.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ringov.yamblzweather.data.database.dao.CityDAO;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.dao.WeatherDAO;
import com.ringov.yamblzweather.data.database.entity.DBCity;
import com.ringov.yamblzweather.data.database.entity.DBFavoriteCity;
import com.ringov.yamblzweather.data.database.entity.DBWeather;

@Database(entities = {DBCity.class, DBWeather.class, DBFavoriteCity.class},
        version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavoriteCityDAO favoriteCityDAO();

    public abstract CityDAO cityDAO();

    public abstract WeatherDAO weatherDAO();
}
