package com.ringov.yamblzweather.ui.weather;

import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

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
        tvTemperature.setText(data.getTemperature() + "");
        tvConditions.setText(data.getConditions());
        tvTime.setText(Utils.getRelativeTime(getContext(), data.getTime()));
        swipeLayout.setRefreshing(false);
    }
}
