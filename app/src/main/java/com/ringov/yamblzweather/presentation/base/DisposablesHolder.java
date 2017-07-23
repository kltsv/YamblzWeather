package com.ringov.yamblzweather.presentation.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 23.07.2017.
 */

public interface DisposablesHolder {

    CompositeDisposable disposables = new CompositeDisposable();

    void addDisposable(Disposable disposable);
}
