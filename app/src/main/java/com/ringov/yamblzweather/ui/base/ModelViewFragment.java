package com.ringov.yamblzweather.ui.base;

import android.arch.lifecycle.Observer;
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

    protected VM viewModel;

    protected abstract Class<VM> getViewModelClass();

    private void attachViewModel() {
        viewModel = ViewModelProviders.of(this).get(getViewModelClass());
    }

    private void startObserve() {
        // TODO rewrite with lambda
        viewModel.getLiveData().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(@Nullable Data data) {
                showDataChanges(data);
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // order matters
        attachViewModel();
        startObserve();
    }

    protected abstract void showDataChanges(Data data);
}
