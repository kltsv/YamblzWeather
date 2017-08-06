package com.ringov.yamblzweather;

import android.app.Activity;
import android.app.Application;

import com.ringov.yamblzweather.dagger.AppInjector;
import com.ringov.yamblzweather.data.database.AppDatabaseCreator;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        Timber.plant(new Timber.DebugTree());

        AppDatabaseCreator.getInstance().createDb(this);
    }

    public void initDagger() {
        AppInjector.init(this);
    }
}
