package com.ringov.yamblzweather.presentation.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.ringov.yamblzweather.dagger.Injectable;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseMvvmActivity<VM extends BaseViewModel>
        extends BaseActivity implements LifecycleRegistryOwner, Injectable {

    private VM viewModel;

    protected abstract Class<VM> getViewModelClass();

    protected CompositeDisposable disposables = new CompositeDisposable();

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(getViewModelClass());
    }

    // Subscribe for user input events in this method
    protected void attachInputListeners() {
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        attachInputListeners();
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();
        disposables.clear();
    }

    protected VM getViewModel() {
        return viewModel;
    }
}
