package com.ringov.yamblzweather.presentation.ui;

import android.content.Context;

import com.ringov.yamblzweather.R;

import java.util.Date;

public final class UIUtils {

    public static String getFormattedTime(Context context, long time) {
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        Date date = new Date(time * 1000);
        return dateFormat.format(date);
    }

    public static float kelvinToCelsius(float kelvin) {
        return kelvin - 273.15f;
    }

    public static String getFormattedTemperature(Context context, float temperature) {
        return context.getString(R.string.wthr_temperature, temperature);
    }
}
