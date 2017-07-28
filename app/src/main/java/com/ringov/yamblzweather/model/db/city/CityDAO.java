package com.ringov.yamblzweather.model.db.city;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Ivan on 28.07.2017.
 */

@Dao
public interface CityDAO {

    @Query("SELECT * FROM cities WHERE city_name LIKE :suggest LIMIT 5")
    List<DBCity> getSuggestions(String suggest);

    @Query("SELECT * FROM cities")
    List<DBCity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DBCity> cities);
}
