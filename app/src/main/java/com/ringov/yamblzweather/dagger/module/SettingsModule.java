package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.domain.settings.SettingsRepository;
import com.ringov.yamblzweather.domain.settings.SettingsRepositoryImpl;

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
