package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.dagger.SchedulerEnum;
import com.ringov.yamblzweather.dagger.SchedulerType;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class RxSchedulerModule {

    @Provides
    @Singleton
    @SchedulerType(scheduler = SchedulerEnum.Main)
    Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @SchedulerType(scheduler = SchedulerEnum.IO)
    Scheduler provideIOScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @SchedulerType(scheduler = SchedulerEnum.Computation)
    Scheduler provideComputationScheduler() {
        return Schedulers.computation();
    }
}
