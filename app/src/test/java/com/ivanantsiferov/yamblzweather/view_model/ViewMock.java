package com.ivanantsiferov.yamblzweather.view_model;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;

import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;

class ViewMock implements LifecycleRegistryOwner {

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

    ViewMock() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }

    private int observeBooleanCalled = 0;
    private int observeThrowableCalled = 0;
    private int observeUIWeatherDetailCalled = 0;

    int getObserveBooleanCalled() {
        return observeBooleanCalled;
    }
    int getObserveThrowableCalled() {
        return observeThrowableCalled;
    }
    int getObserveUIWeatherDetailCalled() {
        return observeUIWeatherDetailCalled;
    }

    void observeBoolean(Boolean value) {
        observeBooleanCalled++;
    }
    void observeThrowable(Throwable value) {
        observeThrowableCalled++;
    }
    void observeUIWeatherDetail(UIWeatherDetail value) {
        observeUIWeatherDetailCalled++;
    }
}
