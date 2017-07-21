package com.ringov.yamblzweather.model.background_service;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by ringov on 15.07.17.
 */

public class WeatherUpdateJobCreator implements JobCreator {
    @Override
    public Job create(String tag) {
        switch (tag) {
            case WeatherUpdateJob.TAG:
                return new WeatherUpdateJob();
            default:
                return null;
        }
    }
}
