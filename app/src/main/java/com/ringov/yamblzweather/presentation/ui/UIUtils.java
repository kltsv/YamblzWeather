package com.ringov.yamblzweather.presentation.ui;

import android.content.Context;

import com.ringov.yamblzweather.Prefs;
import com.ringov.yamblzweather.R;

import java.util.Calendar;
import java.util.Date;

public final class UIUtils {

    private static final float MPH_COEFFICIENT = 2.236936f;

    public static String getFormattedWindSpeed(Context context, float speed) {
        if (isImperial(context)) {
            speed *= MPH_COEFFICIENT;
            return context.getString(R.string.wthr_wind_speed_imperial, speed);
        } else {
            return context.getString(R.string.wthr_wind_speed_metric, speed);
        }
    }

    public static String getFormattedPressure(Context context, float pressure) {
        return context.getString(R.string.wthr_pressure, pressure);
    }

    public static String getFormattedHumidity(Context context, int humidity) {
        return context.getString(R.string.wthr_humidity, humidity);
    }

    public static String getFormattedTime(Context context, long time) {
        Calendar now = Calendar.getInstance();
        Calendar someTime = Calendar.getInstance();
        someTime.setTimeInMillis(time * 1000);
        if (now.get(Calendar.DATE) == someTime.get(Calendar.DATE)) {
            return context.getString(R.string.wthr_today);
        } else {
            java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
            Date date = new Date(time * 1000);
            return dateFormat.format(date);
        }
    }

    public static String getFormattedTemperature(Context context, float temperature) {
        temperature = kelvinToCelsius(temperature);

        if (isImperial(context))
            temperature = celsiusToFahrenheit(temperature);

        return context.getString(R.string.wthr_temperature, temperature);
    }

    // Helper methods
    private static float kelvinToCelsius(float kelvin) {
        return kelvin - 273.15f;
    }

    private static float celsiusToFahrenheit(float celsius) {
        return ((celsius * 9) / 5) + 32;
    }

    private static boolean isImperial(Context context) {
        final String units = Prefs.getString(
                context, R.string.prefs_units_key, R.string.prefs_units_default);
        final String imperial = context.getString(R.string.prefs_units_imperial);
        return units.equals(imperial);
    }
}
