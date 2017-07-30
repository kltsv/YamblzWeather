package com.ringov.yamblzweather.di.location;

import android.support.annotation.NonNull;

import com.ringov.yamblzweather.model.repository.location.LocationRepository;
import com.ringov.yamblzweather.model.repository.location.LocationRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan on 26.07.2017.
 */

@Module
public class LocationModule {

    @Provides
    @NonNull
    @Singleton
    LocationRepository provideLocationRepository() {
        return new LocationRepositoryImpl();
    }
}
