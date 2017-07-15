package com.ringov.yamblzweather.model.background_service;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.db.Database;
import com.ringov.yamblzweather.model.internet.APIFactory;
import com.ringov.yamblzweather.model.internet.WeatherService;
import com.ringov.yamblzweather.model.repositories.weather.WeatherRepository;
import com.ringov.yamblzweather.model.repositories.weather.WeatherRepositoryImpl;

/**
 * Created by ringov on 15.07.17.
 */

public class WeatherUpdateJob extends Job {

    public static final String TAG = "com.ringov.yamblzweather.weather_update_job";

    private int cityId = 524901;

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
        WeatherRepository repository = new WeatherRepositoryImpl();
        return Result.SUCCESS;
    }

    private WeatherService getService() {
        return APIFactory.getRetrofitService(WeatherService.class);
    }
}
