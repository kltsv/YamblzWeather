package com.ringov.yamblzweather.dagger.module;

import android.support.annotation.NonNull;

import com.ringov.yamblzweather.model.repository.settings.SettingsRepository;
import com.ringov.yamblzweather.model.repository.settings.SettingsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {

    @Provides
    @Singleton
    SettingsRepository provideSettingsRepository() {
        return new SettingsRepositoryImpl();
    }
}
