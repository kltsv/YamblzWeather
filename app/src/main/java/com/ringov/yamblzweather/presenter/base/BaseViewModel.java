package com.ringov.yamblzweather.presenter.base;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ringov on 12.07.17.
 */

public class BaseViewModel<LD extends BaseLiveData<D>, D> extends ViewModel {

    CompositeDisposable disposables;

    private BaseLiveData<D> liveData;

    public BaseViewModel() {
        liveData = new BaseLiveData<>();
        disposables = new CompositeDisposable();
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    public BaseLiveData<D> getLiveData() {
        return liveData;
    }

    public void updateValue(D data) {
        liveData.updateValue(data);
    }

    protected void handleError(Throwable t) {

    }
}
