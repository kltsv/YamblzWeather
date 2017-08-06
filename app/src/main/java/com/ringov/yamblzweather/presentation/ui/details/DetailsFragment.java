package com.ringov.yamblzweather.presentation.ui.details;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.dagger.Injectable;
import com.ringov.yamblzweather.presentation.base.BaseMvvmFragment;

import javax.inject.Inject;

public class DetailsFragment extends BaseMvvmFragment<DetailsViewModel> implements Injectable {

    public static final String TAG = "DetailsFragment";

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onViewModelAttach() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
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
