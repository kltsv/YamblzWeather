package com.ringov.yamblzweather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.evernote.android.job.JobManager;
import com.ringov.yamblzweather.dagger.component.AppComponent;
import com.ringov.yamblzweather.dagger.component.DaggerAppComponent;
import com.ringov.yamblzweather.model.background_service.WeatherUpdateJobCreator;
import com.ringov.yamblzweather.model.db.city.CityDatabaseCreator;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static Context getContext() {
        return context;
    }

    private static AppComponent component;
    private AppComponent buildComponent() {
        return DaggerAppComponent.builder().build();
    }
    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        Timber.plant(new Timber.DebugTree());

        CityDatabaseCreator.getInstance().createDb(this);

        component = buildComponent();
        JobManager.create(this).addJobCreator(new WeatherUpdateJobCreator());
    }
}
