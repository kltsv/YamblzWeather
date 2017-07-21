package com.ringov.yamblzweather.ui.base;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ringov.yamblzweather.viewmodel.base.BaseViewModel;
import com.ringov.yamblzweather.viewmodel.base.StateData;

/**
 * Created by ringov on 12.07.17.
 */

public abstract class ModelViewFragment<VM extends BaseViewModel<Data, State>, Data, State extends StateData>
        extends BaseFragment implements BaseView {

    private VM viewModel;

    protected abstract Class<VM> getViewModelClass();

    private void attachViewModel() {
        viewModel = ViewModelProviders.of(this).get(getViewModelClass());
    }

    private void startObserve() {
        viewModel.observe(this, this::showDataChanges, this::showStateChanges);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // order matters
        attachViewModel();
        startObserve();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getViewModel().onLeavingScreen();
    }

    protected VM getViewModel() {
        return viewModel;
    }

    protected abstract void showDataChanges(Data data);

    private void showStateChanges(State state) {
        if (state.isError()) {
            showError(state.getErrorMessage());
        }

        if (state.isLoading()) {
            showLoading();
        } else if (state.isLoaded()) {
            hideLoading();
        }

        // todo handle other cases
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
