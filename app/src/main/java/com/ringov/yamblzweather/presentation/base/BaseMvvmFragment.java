package com.ringov.yamblzweather.presentation.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.ringov.yamblzweather.dagger.Injectable;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseMvvmFragment<VM extends BaseViewModel>
        extends BaseFragment implements LifecycleRegistryOwner, Injectable {

    protected VM viewModel;

    protected CompositeDisposable disposables = new CompositeDisposable();

    protected abstract Class<VM> getViewModelClass();

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

    // Get instance of ViewModel in this method and pass arguments to it if needed
    protected abstract void onViewModelAttach();

    // Subscribe for user input events in this method
    protected abstract void attachInputListeners();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onViewModelAttach();
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        attachInputListeners();
    }

    @Override
    @CallSuper
    public void onPause() {
        super.onPause();
        disposables.clear();
    }

    protected VM getViewModel() {
        return viewModel;
    }
}
