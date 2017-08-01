package com.ringov.yamblzweather.data.db.city;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {DBCity.class}, version = 1, exportSchema = false)
public abstract class CityDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "weather";

    public abstract CityDAO cityDAO();
}
