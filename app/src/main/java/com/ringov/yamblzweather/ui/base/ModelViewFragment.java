package com.ringov.yamblzweather.ui.base;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ringov.yamblzweather.viewmodel.base.BaseLiveData;
import com.ringov.yamblzweather.viewmodel.base.BaseViewModel;

/**
 * Created by ringov on 12.07.17.
 */

public abstract class ModelViewFragment<VM extends BaseViewModel<BaseLiveData<Data>, Data>, Data>
        extends BaseFragment {

    private VM viewModel;

    protected abstract Class<VM> getViewModelClass();

    private void attachViewModel() {
        viewModel = ViewModelProviders.of(this).get(getViewModelClass());
    }

    private void startObserve() {
        viewModel.getLiveData().observe(this, data -> showDataChanges(data));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // order matters
        attachViewModel();
        startObserve();
    }

    protected VM getViewModel() {
        return viewModel;
    }

    protected abstract void showDataChanges(Data data);
}
