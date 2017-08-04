package com.ringov.yamblzweather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.evernote.android.job.JobManager;
import com.ringov.yamblzweather.dagger.component.AppComponent;
import com.ringov.yamblzweather.dagger.component.DaggerAppComponent;
import com.ringov.yamblzweather.dagger.module.ApplicationModule;
import com.ringov.yamblzweather.dagger.module.DatabaseModule;
import com.ringov.yamblzweather.data.background_service.WeatherUpdateJobCreator;
import com.ringov.yamblzweather.data.db.database.AppDatabaseCreator;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static Context getContext() {
        return context;
    }

    public static AppComponent getComponent() {
        return component;
    }
    private static AppComponent component;
    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(AppDatabaseCreator.getInstance().getDatabase()))
                .build();
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

        AppDatabaseCreator.getInstance().createDb(this);

        JobManager.create(this).addJobCreator(new WeatherUpdateJobCreator());

        component = buildComponent();
    }
}
