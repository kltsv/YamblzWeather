package com.ringov.yamblzweather.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ringov.yamblzweather.data.database.entity.DBWeather;

import java.util.List;

import static com.ringov.yamblzweather.data.database.Contract.COLUMN_CITY_ID;
import static com.ringov.yamblzweather.data.database.Contract.COLUMN_TIME;
import static com.ringov.yamblzweather.data.database.Contract.TABLE_FORECAST;

@Dao
public interface WeatherDAO {

    @Query("SELECT * FROM " + TABLE_FORECAST)
    List<DBWeather> getAll();

    @Query("SELECT * FROM " + TABLE_FORECAST + " WHERE " + COLUMN_CITY_ID + " = :cityId " +
            " AND " + COLUMN_TIME + " >= :time ORDER BY " + COLUMN_TIME + " ASC")
    List<DBWeather> getForecast(int cityId, long time);

    @Query("SELECT * FROM " + TABLE_FORECAST + " WHERE " + COLUMN_CITY_ID + " = :cityId " +
            "ORDER BY " + COLUMN_TIME + " ASC LIMIT 1")
    DBWeather getCurrentWeather(int cityId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DBWeather weather);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DBWeather> forecast);
}
