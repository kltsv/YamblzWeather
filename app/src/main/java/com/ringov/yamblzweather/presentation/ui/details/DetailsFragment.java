package com.ringov.yamblzweather.presentation.ui.details;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseMvvmFragment;

public class DetailsFragment extends BaseMvvmFragment<DetailsViewModel> {

    public static final String TAG = "DetailsFragment";

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
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
