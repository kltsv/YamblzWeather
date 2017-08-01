package com.ringov.yamblzweather.presentation.base;

import android.arch.lifecycle.LiveData;

public class BaseLiveData<T> extends LiveData<T> {

    public void updateValue(T value) {
        setValue(value);
    }
}
