package com.ringov.yamblzweather.presenter.base;

import android.arch.lifecycle.LiveData;

/**
 * Created by ringov on 12.07.17.
 */

public class BaseLiveData<T> extends LiveData<T> {

    public void updateValue(T value) {
        setValue(value);
    }

}
