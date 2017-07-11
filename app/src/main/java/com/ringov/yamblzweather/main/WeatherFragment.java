package com.ringov.yamblzweather.main;

import android.view.View;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.base.BaseFragment;
import com.ringov.yamblzweather.routing.ScreenView;

/**
 * Created by ringov on 07.07.17.
 */

public class WeatherFragment extends BaseFragment implements ScreenView<WeatherFragment> {
    @Override
    protected int getLayout() {
        return R.layout.weather_fragment;
    }

    @Override
    protected void initializeViews(View view) {

    }

    @Override
    public WeatherFragment getFragment() {
        return this;
    }
}
