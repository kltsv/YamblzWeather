package com.ringov.yamblzweather;

import android.app.Application;
import android.content.Context;

import com.evernote.android.job.JobManager;
import com.ringov.yamblzweather.di.app.AppComponent;
import com.ringov.yamblzweather.di.app.DaggerAppComponent;
import com.ringov.yamblzweather.di.weather.WeatherModule;
import com.ringov.yamblzweather.model.background_service.WeatherUpdateJobCreator;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ringov on 12.07.17.
 */

public class App extends Application {
    private static Context context;

    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        component = buildComponent();
        JobManager.create(this).addJobCreator(new WeatherUpdateJobCreator());

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .weatherModule(new WeatherModule())
                .build();
    }

    public static Context getContext() {
        return context;
    }
}
