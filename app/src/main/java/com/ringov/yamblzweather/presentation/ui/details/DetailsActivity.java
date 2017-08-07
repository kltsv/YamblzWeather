package com.ringov.yamblzweather.presentation.ui.details;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DetailsActivity extends BaseActivity implements HasSupportFragmentInjector {

    public final static String ARG_TIME = "ARG_TIME";

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            long time = getIntent().getLongExtra(ARG_TIME, -1);
            replaceFragment(DetailsFragment.newInstance(time), FRAGMENT_CONTAINER);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }
}
