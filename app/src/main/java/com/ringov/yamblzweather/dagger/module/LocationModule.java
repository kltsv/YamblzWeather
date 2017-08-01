package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.domain.location.LocationRepository;
import com.ringov.yamblzweather.domain.location.LocationRepositoryImpl;

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
