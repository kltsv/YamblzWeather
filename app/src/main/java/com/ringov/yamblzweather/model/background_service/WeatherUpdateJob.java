package com.ringov.yamblzweather.model.background_service;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.ringov.yamblzweather.model.db.Database;

/**
 * Created by ringov on 15.07.17.
 */

public class WeatherUpdateJob extends Job {

    public static final String TAG = "com.ringov.yamblzweather.weather_update_job";

    public static void scheduleJob() {
        long interval = Database.getInstance().getUpdateInterval();
        new JobRequest.Builder(WeatherUpdateJob.TAG)
                .setPeriodic(interval)
                .build()
                .schedule();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        return Result.SUCCESS;
    }
}
