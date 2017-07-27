package com.ringov.yamblzweather.presentation.base;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ringov on 12.07.17.
 */

public abstract class BaseViewModel extends ViewModel {

    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
