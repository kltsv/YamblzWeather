package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.presentation.ui.details.DetailsActivity;
import com.ringov.yamblzweather.presentation.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract DetailsActivity contributeDetailsActivity();
}
