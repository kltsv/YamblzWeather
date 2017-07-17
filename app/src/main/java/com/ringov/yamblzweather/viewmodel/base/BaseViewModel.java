package com.ringov.yamblzweather.viewmodel.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.ringov.yamblzweather.ui.base.ModelViewFragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ringov on 12.07.17.
 */

public class BaseViewModel<D> extends ViewModel {

    CompositeDisposable disposables;

    private BaseLiveData<D> liveData;

    public BaseViewModel() {
        liveData = new BaseLiveData<>();
        disposables = new CompositeDisposable();
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    public void updateValue(D data) {
        liveData.updateValue(data);
    }

    public void handleError(Throwable t) {
        // todo handle errors
    }

    public void observe(LifecycleOwner owner, Observer<D> observer) {
        liveData.observe(owner, observer);
    }
}
