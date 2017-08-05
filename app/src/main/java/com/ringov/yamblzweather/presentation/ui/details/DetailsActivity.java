package com.ringov.yamblzweather.presentation.ui.details;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseActivity;

import butterknife.BindView;

public class DetailsActivity extends BaseActivity {

    @IdRes
    private static final int FRAGMENT_CONTAINER = R.id.container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayout() {
        return R.layout.activity_generic;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            replaceFragment(DetailsFragment.newInstance(), FRAGMENT_CONTAINER);
        }
    }
}
