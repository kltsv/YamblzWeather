package com.ringov.yamblzweather.domain;

import io.reactivex.Scheduler;

public abstract class BaseRepository {

    protected Scheduler schedulerUI;
    protected Scheduler schedulerIO;
    protected Scheduler schedulerComputation;

    public BaseRepository(Scheduler schedulerUI, Scheduler schedulerIO, Scheduler schedulerComputation) {
        this.schedulerUI = schedulerUI;
        this.schedulerIO = schedulerIO;
        this.schedulerComputation = schedulerComputation;
    }
}
