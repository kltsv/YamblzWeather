package com.ringov.yamblzweather.presentation.ui.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.Utils;
import com.ringov.yamblzweather.presentation.base.BaseFragment;
import com.ringov.yamblzweather.presentation.data.UIWeather;

import butterknife.BindView;

/**
 * Created by ringov on 07.07.17.
 */

public class WeatherFragment extends BaseFragment<WeatherViewModel> {

    public static final String TAG = "WeatherFragment";

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    protected Class<WeatherViewModel> getViewModelClass() {
        return WeatherViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.weather_fragment;
    }

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_conditions)
    TextView tvConditions;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.weather_image)
    ImageView weatherImage;
    @BindView(R.id.tv_location)
    TextView locationTv;

    private SharedPreferences sharedPrefs;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.weather);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currentLocation = sharedPrefs.getString(getString(R.string.prefs_location_key), getString(R.string.prefs_location_default));
        locationTv.setText(currentLocation);
    }

    @Override
    protected void attachInputListeners() {
        getViewModel().observe(this, this::showLoading, this::showWeather, this::showError);

        disposables.add(
                RxSwipeRefreshLayout
                        .refreshes(swipeLayout)
                        .subscribe(o -> getViewModel().onRefresh())
        );
    }

    private void showLoading(boolean isLoading) {
        swipeLayout.setRefreshing(isLoading);
    }

    private void showWeather(UIWeather data) {
        tvTemperature.setText(Utils.getFormattedTemperature(getContext(), data.getTemperature()));
        tvConditions.setText(data.getConditionName());
        tvTime.setText(Utils.getRelativeTime(getContext(), data.getTime()));
        weatherImage.setImageResource(data.getConditionImage());
    }

    private void showError(Throwable error) {
        error.printStackTrace();
    }
}
