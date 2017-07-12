package com.ringov.yamblzweather.ui.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
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
        extends BaseFragment /*implements LifecycleOwner*/ {

    // не используется собственная реализация, потому что данные не обновляются (причину не нашел)
    //private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    protected VM viewModel;

/*    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }*/

    protected abstract Class<VM> getViewModelClass();

    private void attachViewModel() {
        viewModel = ViewModelProviders.of(this).get(getViewModelClass());
    }

    private void startObserve() {
        // TODO переписать с лямбдами
        viewModel.getLiveData().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(@Nullable Data data) {
                onDataChanged(data);
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // порядок имеет значение
        attachViewModel();
        startObserve();
    }

    protected abstract void onDataChanged(Data data);
}
