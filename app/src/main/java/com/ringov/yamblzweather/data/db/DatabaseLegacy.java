package com.ringov.yamblzweather.data.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.data.db.data.DBWeather;

import java.util.concurrent.TimeUnit;

public class DatabaseLegacy {

    private static final String SB_SHARED_PREFS = "com.ringov.yamblzweather.db_shared_prefs";
    private static final String TIME_KEY = "time";
    private static final String CONDITION_KEY = "condition";
    private static final String TEMPERATURE_KEY = "temperature";

    private static DatabaseLegacy instance;

    public static DatabaseLegacy getInstance() {
        if (instance == null) {
            instance = new DatabaseLegacy();
        }

        return instance;
    }

    public void saveWeather(DBWeather weather) {
        SharedPreferences sp = App.getContext().getSharedPreferences(SB_SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(TIME_KEY, weather.getTime());
        editor.putInt(CONDITION_KEY, weather.getConditionId());
        editor.putFloat(TEMPERATURE_KEY, weather.getTemperature());
        editor.apply();
    }

    public DBWeather loadWeather() {
        SharedPreferences sp = App.getContext().getSharedPreferences(SB_SHARED_PREFS, Context.MODE_PRIVATE);
        long time = sp.getLong(TIME_KEY, 0);
        int condition = sp.getInt(CONDITION_KEY, 0);
        float temperature = sp.getFloat(TEMPERATURE_KEY, 0);
        return new DBWeather.Builder(time)
                .temperature(temperature)
                .weatherCondition(condition)
                .build();
    }

    public int getUserCityId() {
        Context context = App.getContext();
        final String KEY = context.getString(R.string.prefs_location_key);
        final int DEFAULT_VALUE = context.getResources().getInteger(R.integer.prefs_location_default);
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY, DEFAULT_VALUE);
    }

    public void putUserCity(int cityId) {
        Context context = App.getContext();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(App.getContext().getString(R.string.prefs_location_key), cityId);
        editor.apply();
    }

    public long getUpdateInterval() {
        Context context = App.getContext();
        final String KEY = context.getString(R.string.prefs_update_intervals_key);
        final String DEFAULT_VALUE = context.getString(R.string.prefs_update_intervals_default);
        String value = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY, DEFAULT_VALUE);
        int minutes = Integer.parseInt(value);
        return TimeUnit.MINUTES.toMillis(minutes);
    }

    public boolean isNotificationEnabled() {
        Context context = App.getContext();
        final String KEY = context.getString(R.string.prefs_update_notifications_key);
        final String DEFAULT_VALUE_STR = context.getString(R.string.prefs_update_notifications_default);
        final boolean DEFAULT_VALUE = Boolean.parseBoolean(DEFAULT_VALUE_STR);
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY, DEFAULT_VALUE);
    }
}
