package com.ringov.yamblzweather.presentation.ui;

import android.content.Context;
import android.text.format.DateUtils;

import com.ringov.yamblzweather.R;

import java.util.concurrent.TimeUnit;

public final class UIUtils {

    public static String getRelativeTime(Context context, long time) {
        long now = System.currentTimeMillis();
        long diff = now - time;

        if (diff < TimeUnit.MINUTES.toMillis(1)) {
            return context.getString(R.string.wthr_right_now);
        } else {
            return DateUtils
                    .getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS).toString();
        }
    }

    public static String getFormattedTemperature(Context context, float temperature) {
        return context.getString(R.string.wthr_temperature, temperature);
    }
}
