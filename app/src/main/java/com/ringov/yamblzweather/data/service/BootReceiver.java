package com.ringov.yamblzweather.data.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ringov.yamblzweather.Prefs;
import com.ringov.yamblzweather.R;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in the
 * application's manifest file. When the user sets the alarm, the receiver is enabled.
 * When the user cancels the alarm, the receiver is disabled, so that rebooting the
 * device will not trigger this receiver.
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    AlarmReceiver alarm = new AlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        final String interval = Prefs.getString(
                context, R.string.prefs_interval_key, R.string.prefs_interval_default);

        if (intent.getAction().equals(BOOT_COMPLETED)) {
            alarm.setAlarm(context, Integer.valueOf(interval));
        }
    }
}
