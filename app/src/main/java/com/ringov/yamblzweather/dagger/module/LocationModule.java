package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.data.db.database.dao.CityDAO;
import com.ringov.yamblzweather.domain.location.LocationRepository;
import com.ringov.yamblzweather.domain.location.LocationRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = DatabaseModule.class)
public class LocationModule {

    @Provides
    @Singleton
    LocationRepository provideLocationRepository(CityDAO cityDAO) {
        return new LocationRepositoryImpl(cityDAO);
    }
}
