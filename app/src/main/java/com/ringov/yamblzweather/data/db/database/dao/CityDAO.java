package com.ringov.yamblzweather.data.db.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ringov.yamblzweather.data.db.database.entity.DBCity;

import java.util.List;

import static com.ringov.yamblzweather.data.db.database.Contract.COLUMN_CITY_ID;
import static com.ringov.yamblzweather.data.db.database.Contract.COLUMN_CITY_NAME;
import static com.ringov.yamblzweather.data.db.database.Contract.TABLE_CITIES;

@Dao
public interface CityDAO {

    @Query("SELECT * FROM " + TABLE_CITIES + " WHERE " + COLUMN_CITY_NAME + " LIKE :suggest LIMIT :limit")
    List<DBCity> getSuggestions(String suggest, int limit);

    @Query("SELECT * FROM " + TABLE_CITIES)
    List<DBCity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DBCity> cities);

    @Query("SELECT * FROM " + TABLE_CITIES + " WHERE " + COLUMN_CITY_NAME + " LIKE :name LIMIT 1")
    DBCity getByName(String name);

    @Query("SELECT * FROM " + TABLE_CITIES + " WHERE " + COLUMN_CITY_ID + " LIKE :id LIMIT 1")
    DBCity getById(long id);
}
