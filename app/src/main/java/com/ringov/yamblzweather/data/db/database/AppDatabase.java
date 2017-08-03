package com.ringov.yamblzweather.data.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ringov.yamblzweather.data.db.database.dao.CityDAO;
import com.ringov.yamblzweather.data.db.database.dao.WeatherDAO;
import com.ringov.yamblzweather.data.db.database.entity.DBCity;
import com.ringov.yamblzweather.data.db.database.entity.DBWeather;

@Database(entities = {DBCity.class, DBWeather.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CityDAO cityDAO();

    public abstract WeatherDAO weatherDAO();
}
