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
import com.ringov.yamblzweather.MainActivity;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.model.db.Database;
import com.ringov.yamblzweather.model.repositories.weather.WeatherRepository;
import com.ringov.yamblzweather.ui.Utils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by ringov on 15.07.17.
 */

public class WeatherUpdateJob extends Job {

    public static final String TAG = "com.ringov.yamblzweather.weather_update_job";

    private int cityId = 524901;

    private int notificationId = 0;

    @Inject
    WeatherRepository weatherRepository;

    public static void schedule() {
        long interval = Database.getInstance().getUpdateInterval();
        JobRequest job = new JobRequest.Builder(WeatherUpdateJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5)) // test 15 min
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
        weatherRepository.updateWeatherInfo()
                .subscribe(weatherInfo -> {

                    String formattedTemperature = Utils.getFormattedTemperature(getContext(), weatherInfo.getTemperature());
                    String condition = getContext().getString(weatherInfo.getCondition());
                    StringBuilder sb = new StringBuilder();
                    sb.append(getContext().getString(R.string.notification_message, formattedTemperature, condition));

                    PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
                            new Intent(getContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification notification = new NotificationCompat.Builder(getContext())
                            .setContentTitle(getContext().getString(R.string.app_name))
                            .setContentIntent(pi)
                            .setContentText(sb.toString())
                            .setAutoCancel(true)
                            .setSmallIcon(R.mipmap.launch_icon_round)
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
