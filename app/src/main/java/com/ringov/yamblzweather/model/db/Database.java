package com.ringov.yamblzweather.model.db;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.TimeUtils;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.model.db.model.DBWeather;

import java.util.concurrent.TimeUnit;

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

    public long getUpdateInterval() {
        Context context = App.getContext();
        final String KEY = context.getString(R.string.update_intervals_key);
        final String DEFAULT_VALUE = context.getString(R.string.default_update_intervals_value);
        String value = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY, DEFAULT_VALUE);
        int hours = Integer.parseInt(value);
        return TimeUnit.HOURS.toMillis(hours);
    }
}
