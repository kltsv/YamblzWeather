package com.ringov.yamblzweather.model.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.model.db.data.DBWeather;

import java.util.concurrent.TimeUnit;

/**
 * Created by ringov on 15.07.17.
 */

public class Database {
    private static final String SB_SHARED_PREFS = "com.ringov.yamblzweather.db_shared_prefs";
    private static final String TIME_KEY = "time";
    private static final String CONDITION_KEY = "condition";
    private static final String TEMPERATURE_KEY = "temperature";

    private static Database instance;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Database() {

    }

    public void saveWeather(DBWeather weather) {
        SharedPreferences sp = App.getContext().getSharedPreferences(SB_SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(TIME_KEY, weather.getTime());
        editor.putInt(CONDITION_KEY, weather.getConditionId());
        editor.putFloat(TEMPERATURE_KEY, weather.getTemperature());
        editor.apply();
    }

    public DBWeather loadWeather(int cityId) {
        SharedPreferences sp = App.getContext().getSharedPreferences(SB_SHARED_PREFS, Context.MODE_PRIVATE);
        long time = sp.getLong(TIME_KEY, System.currentTimeMillis());
        int condition = sp.getInt(CONDITION_KEY, 0);
        float temperature = sp.getFloat(TEMPERATURE_KEY, 0);
        return new DBWeather.Builder(time)
                .temperature(temperature)
                .weatherCondition(condition)
                .build();
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
