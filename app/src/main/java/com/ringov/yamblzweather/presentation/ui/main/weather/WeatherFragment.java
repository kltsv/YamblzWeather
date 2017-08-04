package com.ringov.yamblzweather.presentation.ui.main.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

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
        return R.layout.fragment_weather;
    }

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rv_forecast)
    RecyclerView forecastRecycler;

    private WeatherAdapter weatherAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_weather);

        weatherAdapter = new WeatherAdapter(getContext(), new ArrayList<>());

        forecastRecycler.setHasFixedSize(true);
        forecastRecycler.setAdapter(weatherAdapter);
    }

    @Override
    protected void attachInputListeners() {


        // Listen for swipe to refresh
        disposables.add(
                RxSwipeRefreshLayout
                        .refreshes(swipeLayout)
                        .subscribe(o -> getViewModel().onRefresh())
        );
    }

    private void showLoading(boolean isLoading) {
        swipeLayout.setRefreshing(isLoading);
    }

    private void showError(Throwable error) {
        error.printStackTrace();
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
