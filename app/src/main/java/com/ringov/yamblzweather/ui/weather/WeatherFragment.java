package com.ringov.yamblzweather.ui.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ringov.yamblzweather.MainViewUpdater;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.ui.Utils;
import com.ringov.yamblzweather.viewmodel.data.WeatherInfo;
import com.ringov.yamblzweather.viewmodel.weather.WeatherViewModel;
import com.ringov.yamblzweather.ui.base.ModelViewFragment;

import butterknife.BindView;

/**
 * Created by ringov on 07.07.17.
 */

public class WeatherFragment extends ModelViewFragment<WeatherViewModel, WeatherInfo> {

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

    MainViewUpdater updater;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainViewUpdater) {
            updater = (MainViewUpdater) context;
        }
    }

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
        swipeLayout.setOnRefreshListener(this::onRefresh);
    }

    private void onRefresh() {
        getViewModel().onRefresh();
    }

    @Override
    protected void showDataChanges(WeatherInfo data) {
        tvTemperature.setText(Utils.getFormattedTemperature(getContext(), data.getTemperature()));
        tvConditions.setText(data.getCondition());
        tvTime.setText(Utils.getRelativeTime(getContext(), data.getTime()));
        weatherImage.setImageResource(data.getConditionImage());

        if (updater != null) {
            updater.onWeatherUpdate(data);
        }

        swipeLayout.setRefreshing(false);
    }
}
