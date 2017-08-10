package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.data.service.BackgroundService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract BackgroundService contributeBackgroundService();
}
