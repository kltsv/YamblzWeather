package com.ringov.yamblzweather.presentation.ui;

import android.content.Context;

import com.ringov.yamblzweather.R;

import java.util.Calendar;
import java.util.Date;

public final class UIUtils {

    public static String getFormattedWindSpeed(Context context, float speed) {
        return context.getString(R.string.wthr_wind_speed, speed);
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
        return context.getString(R.string.wthr_temperature, temperature);
    }

    private static float kelvinToCelsius(float kelvin) {
        return kelvin - 273.15f;
    }
}
