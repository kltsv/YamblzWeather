package com.ringov.yamblzweather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.evernote.android.job.JobManager;
import com.ringov.yamblzweather.di.app.AppComponent;
import com.ringov.yamblzweather.di.app.DaggerAppComponent;
import com.ringov.yamblzweather.di.weather.WeatherModule;
import com.ringov.yamblzweather.model.background_service.WeatherUpdateJobCreator;
import com.ringov.yamblzweather.presentation.data.UIWeather;
import com.squareup.leakcanary.LeakCanary;

import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by ringov on 12.07.17.
 */

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    public static BehaviorSubject<UIWeather> uiWeatherSubject;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        uiWeatherSubject = BehaviorSubject.create();

        component = buildComponent();
        JobManager.create(this).addJobCreator(new WeatherUpdateJobCreator());
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
