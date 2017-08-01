package com.ringov.yamblzweather.model.db.city;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CityDAO {

    @Query("SELECT * FROM cities WHERE city_name LIKE :suggest LIMIT :limit")
    List<DBCity> getSuggestions(String suggest, int limit);

    @Query("SELECT * FROM cities")
    List<DBCity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DBCity> cities);

    @Query("SELECT * FROM cities WHERE city_name LIKE :name LIMIT 1")
    DBCity getByName(String name);

    @Query("SELECT * FROM cities WHERE city_id LIKE :id LIMIT 1")
    DBCity getById(long id);
}
