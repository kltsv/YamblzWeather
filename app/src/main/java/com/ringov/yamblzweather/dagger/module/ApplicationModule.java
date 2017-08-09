package com.ringov.yamblzweather.dagger.module;

import android.app.Application;
import android.content.Context;

import com.ringov.yamblzweather.data.background.AlarmReceiver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Singleton
    @Provides
    AlarmReceiver provideAlarmReceiver() {
        return new AlarmReceiver();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
