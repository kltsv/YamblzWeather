package com.ringov.yamblzweather.model.db;

import com.ringov.yamblzweather.model.db.model.DBWeather;

/**
 * Created by ringov on 15.07.17.
 */

public class Database {
    private static Database instance;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Database() {

    }

    public DBWeather getWeather(int cityId) {
        return new DBWeather();
    }
}
