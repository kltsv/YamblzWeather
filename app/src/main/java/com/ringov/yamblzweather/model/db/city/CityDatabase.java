package com.ringov.yamblzweather.model.db.city;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Ivan on 28.07.2017.
 */

@Database(entities = {DBCity.class}, version = 1, exportSchema = false)
public abstract class CityDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "prefilled.db";

    public abstract CityDAO cityDAO();
}
