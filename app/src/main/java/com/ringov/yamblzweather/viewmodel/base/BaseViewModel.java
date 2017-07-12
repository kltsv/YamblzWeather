package com.ringov.yamblzweather.viewmodel.base;

import android.arch.lifecycle.ViewModel;

/**
 * Created by ringov on 12.07.17.
 */

public class BaseViewModel<LD extends BaseLiveData<D>, D> extends ViewModel {

    private BaseLiveData<D> liveData;

    public BaseViewModel() {
        liveData = new BaseLiveData<>();
    }

    public BaseLiveData<D> getLiveData() {
        return liveData;
    }

    public void updateValue(D data) {
        liveData.updateValue(data);
    }
}
