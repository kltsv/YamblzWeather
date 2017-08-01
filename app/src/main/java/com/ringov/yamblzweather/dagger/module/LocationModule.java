package com.ringov.yamblzweather.dagger.module;

import android.support.annotation.NonNull;

import com.ringov.yamblzweather.model.repository.location.LocationRepository;
import com.ringov.yamblzweather.model.repository.location.LocationRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {

    @Provides
    @Singleton
    LocationRepository provideLocationRepository() {
        return new LocationRepositoryImpl();
    }
}
