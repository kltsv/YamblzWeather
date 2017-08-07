package com.ringov.yamblzweather.presentation.ui.details;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseMvvmFragment;

import javax.inject.Inject;

public class DetailsFragment extends BaseMvvmFragment<DetailsViewModel> {

    public static final String TAG = "DetailsFragment";

    private static final String ARG_TIME = "ARG_TIME";
    private static final String ARG_CITY_ID = "ARG_CITY_ID";

    public static DetailsFragment newInstance(long time, int cityId) {
        if (time == -1 || cityId == -1)
            throw new IllegalArgumentException("Trying to create instance with wrong arguments");

        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TIME, time);
        args.putInt(ARG_CITY_ID, cityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onViewModelAttach() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
        long time = getArguments().getLong(ARG_TIME);
        int cityId = getArguments().getInt(ARG_CITY_ID);
        viewModel.showWeatherFor(time, cityId);
    }

    @Override
    protected int getLayout() {
        return R.layout.details_fragment;
    }

    @Override
    protected Class<DetailsViewModel> getViewModelClass() {
        return DetailsViewModel.class;
    }
}
