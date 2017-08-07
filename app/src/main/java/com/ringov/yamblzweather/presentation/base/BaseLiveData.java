package com.ringov.yamblzweather.presentation.base;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

public class BaseLiveData<T> extends LiveData<T> {

    public void updateValue(@NonNull T value) {
        setValue(value);
    }
}
