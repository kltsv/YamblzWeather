package com.ringov.yamblzweather.presentation.base;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseFragment<VM extends BaseViewModel> extends LifecycleFragment {

    private VM viewModel;
    private Unbinder unbinder;

    protected CompositeDisposable disposables = new CompositeDisposable();

    @LayoutRes
    protected abstract int getLayout();

    protected abstract Class<VM> getViewModelClass();

    @Nullable
    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected VM getViewModel() {
        return viewModel;
    }
}
