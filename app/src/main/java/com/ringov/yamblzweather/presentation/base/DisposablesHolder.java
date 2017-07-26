package com.ringov.yamblzweather.presentation.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 23.07.2017.
 */

/**
 * Holds Disposables with input events/data requests/etc subscriptions.
 * Easy unsubscribe from all subscriptions if needed.
 */
public interface DisposablesHolder {

    CompositeDisposable disposables = new CompositeDisposable();

    void addDisposable(Disposable disposable);
}
