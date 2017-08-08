package com.ringov.yamblzweather.presentation.ui.main.forecast;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseMvvmFragment;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

public class ForecastFragment extends BaseMvvmFragment<ForecastViewModel> {

    public static final String TAG = "ForecastFragment";

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected Class<ForecastViewModel> getViewModelClass() {
        return ForecastViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_weather;
    }

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rv_forecast)
    RecyclerView forecastRecycler;

    private ForecastAdapter forecastAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_forecast);

        forecastAdapter = new ForecastAdapter(getContext(), new ArrayList<>());

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        forecastRecycler.addItemDecoration(itemDecoration);
        forecastRecycler.setHasFixedSize(true);
        forecastRecycler.setAdapter(forecastAdapter);
    }

    @Override
    protected void attachInputListeners() {
        getViewModel().observe(this, this::showLoading, this::showForecast, this::showError);

        // Listen for swipe to refresh
        disposables.add(
                RxSwipeRefreshLayout
                        .refreshes(swipeLayout)
                        .subscribe(o -> getViewModel().onRefresh())
        );

        // Listen for item clicks
        disposables.add(
                forecastAdapter.getOnItemClickObservable()
                        .subscribe(item -> viewModel.openWeatherDetails(item))
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        forecastAdapter.destroy();
    }

    @Override
    protected void onViewModelAttach() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
    }

    private void showForecast(List<UIWeatherList> forecast) {
        forecastAdapter.replace(forecast);
    }

    private void showLoading(boolean isLoading) {
        swipeLayout.setRefreshing(isLoading);
    }

    private void showError(Throwable error) {
        Timber.e(error);
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
