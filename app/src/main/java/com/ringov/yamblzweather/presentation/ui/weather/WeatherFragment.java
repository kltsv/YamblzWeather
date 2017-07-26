package com.ringov.yamblzweather.presentation.ui.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.Utils;
import com.ringov.yamblzweather.presentation.data.UIWeather;
import com.ringov.yamblzweather.presentation.base.ModelViewFragment;

import butterknife.BindView;

/**
 * Created by ringov on 07.07.17.
 */

public class WeatherFragment extends ModelViewFragment<WeatherViewModel, UIWeather, WeatherStateData> implements WeatherView {

    public static final String TAG = "weather";

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
    protected int getLayout() {
        return R.layout.weather_fragment;
    }

    @Override
    protected Class<WeatherViewModel> getViewModelClass() {
        return WeatherViewModel.class;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.weather);
        swipeLayout.setOnRefreshListener(this::onRefresh);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currentLocation = sharedPrefs.getString(getString(R.string.prefs_location_key), getString(R.string.prefs_location_default));
        locationTv.setText(currentLocation);
    }

    private void onRefresh() {
        getViewModel().onRefresh();
    }

    @Override
    protected void showDataChanges(UIWeather data) {
        tvTemperature.setText(Utils.getFormattedTemperature(getContext(), data.getTemperature()));
        tvConditions.setText(data.getConditionName());
        tvTime.setText(Utils.getRelativeTime(getContext(), data.getTime()));
        weatherImage.setImageResource(data.getConditionImage());

        App.uiWeatherSubject.onNext(data);
    }

    @Override
    public void showLoading() {
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeLayout.setRefreshing(false);
    }
}
