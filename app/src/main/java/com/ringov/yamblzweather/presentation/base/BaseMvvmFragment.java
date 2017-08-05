package com.ringov.yamblzweather.presentation.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseMvvmFragment<VM extends BaseViewModel>
        extends BaseFragment implements LifecycleRegistryOwner {

    private VM viewModel;

    protected CompositeDisposable disposables = new CompositeDisposable();

    protected abstract Class<VM> getViewModelClass();

    LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

    // Subscribe for user input events in this method
    protected void attachInputListeners() {
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        // Order matters
        viewModel = ViewModelProviders.of(this).get(getViewModelClass());
        attachInputListeners();
    }

    @Override
    @CallSuper
    public void onStop() {
        super.onStop();
        disposables.clear();
    }

    protected VM getViewModel() {
        return viewModel;
    }
}
