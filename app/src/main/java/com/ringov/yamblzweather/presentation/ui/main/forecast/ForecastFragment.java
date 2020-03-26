package com.ringov.yamblzweather.presentation.ui.main.forecast;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.domain.exceptions.NoInternetConnectionException;
import com.ringov.yamblzweather.domain.exceptions.StubException;
import com.ringov.yamblzweather.presentation.base.BaseMvvmFragment;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

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
        return R.layout.fragment_forecast;
    }

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rv_forecast)
    RecyclerView forecastRecycler;
    @BindView(R.id.ll_empty)
    LinearLayout emptyLinearLayout;

    private ForecastAdapter forecastAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        forecastAdapter = new ForecastAdapter();

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        forecastRecycler.addItemDecoration(itemDecoration);
        forecastRecycler.setHasFixedSize(true);
        forecastRecycler.setAdapter(forecastAdapter);
    }

    @Override
    protected void attachInputListeners() {
        getViewModel().observe(
                this, this::showLoading, this::showForecast, this::showError, this::showSelectedCity);

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
        showHideEmptyState(forecast.isEmpty());
        forecastAdapter.replace(forecast);
    }

    private void showLoading(boolean isLoading) {
        swipeLayout.setRefreshing(isLoading);
    }

    private void showError(Throwable error) {
        // Because this method get called with old data each time, when fragment resumes,
        // stub exception is thrown to override previous exception, that has been shown to user
        if (error instanceof StubException)
            return;

        if (error instanceof NoInternetConnectionException)
            toast(R.string.error_no_internet_connection);
        else if (error instanceof UnknownHostException)
            toast(R.string.error_request_failed);
        else if (error instanceof SocketTimeoutException)
            toast(R.string.error_request_failed);
        else
            throw new RuntimeException(error);
    }

    private void showSelectedCity(String city) {
        getActivity().setTitle(city);
    }

    private void showHideEmptyState(boolean show) {
        if (show)
            emptyLinearLayout.setVisibility(View.VISIBLE);
        else
            emptyLinearLayout.setVisibility(View.GONE);
    }
}
