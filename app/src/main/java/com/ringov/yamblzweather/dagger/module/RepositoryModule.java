package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.dagger.SchedulerEnum;
import com.ringov.yamblzweather.dagger.SchedulerType;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.dao.WeatherDAO;
import com.ringov.yamblzweather.data.networking.WeatherAPI;
import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.domain.repository.weather.WeatherRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module(includes = {RxSchedulerModule.class, DatabaseModule.class, NetworkModule.class})
public class RepositoryModule {

    @Provides
    WeatherRepository provideWeatherRepository(
            @SchedulerType(scheduler = SchedulerEnum.Main) Scheduler schedulerUI,
            @SchedulerType(scheduler = SchedulerEnum.IO) Scheduler schedulerIO,
            @SchedulerType(scheduler = SchedulerEnum.Computation) Scheduler schedulerComputation,
            WeatherDAO weatherDAO, FavoriteCityDAO favoriteCityDAO, WeatherAPI weatherAPI
    ) {
        return new WeatherRepositoryImpl(
                schedulerUI, schedulerIO, schedulerComputation,
                weatherDAO, favoriteCityDAO, weatherAPI
        );
    }
}
