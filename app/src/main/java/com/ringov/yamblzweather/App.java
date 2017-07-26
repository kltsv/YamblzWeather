package com.ringov.yamblzweather;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.evernote.android.job.JobManager;
import com.ringov.yamblzweather.di.app.AppComponent;
import com.ringov.yamblzweather.di.app.DaggerAppComponent;
import com.ringov.yamblzweather.di.app.NavigationModule;
import com.ringov.yamblzweather.model.background_service.WeatherUpdateJobCreator;
import com.ringov.yamblzweather.navigation.RouterHolder;
import com.ringov.yamblzweather.navigation.base.NavigatorBinder;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ringov on 12.07.17.
 */

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static Context getContext() {
        return context;
    }

    private static AppComponent component;
    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .navigationModule(new NavigationModule(routerHolder))
                .build();
    }
    public static AppComponent getComponent() {
        return component;
    }

    private static RouterHolder routerHolder;
    public static NavigatorBinder getNavigationBinder() {
        return routerHolder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        routerHolder = new RouterHolder();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        component = buildComponent();
        JobManager.create(this).addJobCreator(new WeatherUpdateJobCreator());
    }
}
