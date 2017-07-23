package com.ringov.yamblzweather.ui;

import android.content.Context;
import android.text.format.DateUtils;

import com.ringov.yamblzweather.R;

import java.util.concurrent.TimeUnit;

/**
 * Created by ringov on 15.07.17.
 */

public class Utils {

    public static String getRelativeTime(Context context, long time) {
        long now = System.currentTimeMillis();
        long diff = now - time;
        if (diff < TimeUnit.MINUTES.toMillis(1)) {
            return context.getString(R.string.right_now);
        } else {
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS).toString();
        }
    }

    public static String getFormattedTemperature(Context context, float temperature) {
        return context.getString(R.string.temperature, temperature);
    }
}
