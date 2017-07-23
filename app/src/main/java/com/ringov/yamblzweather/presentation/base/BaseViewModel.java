package com.ringov.yamblzweather.presentation.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.ringov.yamblzweather.model.internet.exceptions.NoInternetConnectionException;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ringov on 12.07.17.
 */

public abstract class BaseViewModel<D, S extends StateData> extends ViewModel implements DisposablesHolder {

    private BaseLiveData<D> liveData;
    private BaseLiveData<S> stateData;
    private S state;

    public BaseViewModel() {
        liveData = new BaseLiveData<>();
        stateData = new BaseLiveData<>();
        state = getInitialState();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    protected <T> ObservableTransformer<T, T> updateStateChanges() {
        return observable -> observable
                .doOnSubscribe(data -> this.showLoading())
                .doOnTerminate(this::hideLoading);
    }

    private void showLoading() {
        state.loading();
        // todo fix triple update
        updateState(state);
    }

    private void hideLoading() {
        state.loaded();
        // todo fix triple update
        updateState(state);
    }

    private void showError(String message) {
        state.error(message);
        state.loaded();
        // todo fix triple update
        updateState(state);
    }

    private void updateState(S state) {
        stateData.updateValue(state);
    }

    public void updateValue(D data) {
        liveData.updateValue(data);
    }

    public void handleError(Throwable t) {
        if (t instanceof NoInternetConnectionException) {
            showError(t.getMessage());
        } else {
            // todo handle other types of errors
        }
    }

    protected abstract S getInitialState();

    public void observe(LifecycleOwner owner, Observer<D> dataObserver, Observer<S> stateObserver) {
        liveData.observe(owner, dataObserver);
        stateData.observe(owner, stateObserver);
    }

    public void onLeavingScreen() {
        disposables.clear();
    }
}
