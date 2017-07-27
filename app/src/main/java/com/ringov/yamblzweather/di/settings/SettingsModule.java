package com.ringov.yamblzweather.di.settings;

import android.support.annotation.NonNull;

import com.ringov.yamblzweather.model.repository.settings.SettingsRepository;
import com.ringov.yamblzweather.model.repository.settings.SettingsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ringov on 16.07.17.
 */

@Module
public class SettingsModule {

    @Provides
    @NonNull
    @Singleton
    SettingsRepository provideSettingsRepository() {
        return new SettingsRepositoryImpl();
    }
}
