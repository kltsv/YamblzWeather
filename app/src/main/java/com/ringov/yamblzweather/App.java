package com.ringov.yamblzweather;

import android.app.Application;
import android.content.Context;

import com.evernote.android.job.JobManager;
import com.ringov.yamblzweather.di.app.AppComponent;
import com.ringov.yamblzweather.di.app.DaggerAppComponent;
import com.ringov.yamblzweather.di.weather.WeatherModule;
import com.ringov.yamblzweather.model.background_service.WeatherUpdateJobCreator;

/**
 * Created by ringov on 12.07.17.
 */

public class App extends Application {
    private static Application application;

    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        component = buildComponent();
        JobManager.create(this).addJobCreator(new WeatherUpdateJobCreator());
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .weatherModule(new WeatherModule())
                .build();
    }

    public static Context getContext() {
        return application;
    }
}
