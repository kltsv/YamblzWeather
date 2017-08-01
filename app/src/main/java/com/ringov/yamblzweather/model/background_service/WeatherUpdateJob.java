package com.ringov.yamblzweather.model.background_service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.presentation.ui.MainActivity;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.model.db.Database;
import com.ringov.yamblzweather.model.repository.settings.SettingsRepository;
import com.ringov.yamblzweather.model.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.presentation.Utils;

import javax.inject.Inject;

public class WeatherUpdateJob extends Job {

    public static final String TAG = "com.ringov.yamblzweather.weather_update_job";
    public static final int FLEX = 300000;

    private int notificationId = 0;

    @Inject
    WeatherRepository weatherRepository;
    @Inject
    SettingsRepository settings;

    public static void schedule() {
        long interval = Database.getInstance().getUpdateInterval();
        JobRequest job = new JobRequest.Builder(WeatherUpdateJob.TAG)
                .setPeriodic(interval, FLEX)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setPersisted(true)
                .setUpdateCurrent(true)
                .build();
        job.schedule();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        App.getComponent().inject(this);
        weatherRepository.updateWeatherIfDataIsOld()
                .filter(w -> settings.isNotificationsEnabled())
                .subscribe(weatherInfo -> {
                    String formattedTemperature = Utils.getFormattedTemperature(getContext(), weatherInfo.getTemperature());
                    String condition = getContext().getString(weatherInfo.getConditionName());
                    StringBuilder sb = new StringBuilder();
                    sb.append(getContext().getString(R.string.notification_message, formattedTemperature, condition));

                    PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
                            new Intent(getContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification notification = new NotificationCompat.Builder(getContext())
                            .setContentTitle(getContext().getString(R.string.app_name))
                            .setContentIntent(pi)
                            .setContentText(sb.toString())
                            .setAutoCancel(true)
                            .setSmallIcon(weatherInfo.getConditionImage())
                            .setShowWhen(true)
                            .setLocalOnly(true)
                            .build();
                    NotificationManagerCompat.from(getContext())
                            .notify(notificationId, notification);
                }, this::handleError);
        return Result.SUCCESS;
    }

    private void handleError(Throwable throwable) {
    }
}
