package com.ringov.yamblzweather.presentation.ui.main.settings;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseMvvmFragment;

import javax.inject.Inject;

public class SettingsFragment extends BaseMvvmFragment<SettingsViewModel> {

    public static final String TAG = "SettingsFragment";

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected int getLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    protected Class<SettingsViewModel> getViewModelClass() {
        return SettingsViewModel.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.settings_title);
    }

    @Override
    protected void onViewModelAttach() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
    }

    @Override
    protected void attachInputListeners() {
        //
    }
}
