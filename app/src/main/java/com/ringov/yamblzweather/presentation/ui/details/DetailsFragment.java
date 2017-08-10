package com.ringov.yamblzweather.presentation.ui.details;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseMvvmFragment;
import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;
import com.ringov.yamblzweather.presentation.ui.UIUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class DetailsFragment extends BaseMvvmFragment<DetailsViewModel> {

    public static final String TAG = "DetailsFragment";

    private static final String ARG_TIME = "ARG_TIME";

    public static DetailsFragment newInstance(long time) {
        if (time == -1)
            throw new IllegalArgumentException("Trying to create instance with wrong arguments");

        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TIME, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_details;
    }

    @Override
    protected Class<DetailsViewModel> getViewModelClass() {
        return DetailsViewModel.class;
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_error)
    TextView errorTextView;
    @BindView(R.id.im_condition)
    ImageView conditionImageView;
    @BindView(R.id.tv_temp_max)
    TextView tempMaxTextView;
    @BindView(R.id.tv_temp_min)
    TextView tempMinTextView;
    @BindView(R.id.tv_humidity)
    TextView humidityTextView;
    @BindView(R.id.tv_pressure)
    TextView pressureTextView;
    @BindView(R.id.im_wind_direction)
    ImageView windDirectionImageView;
    @BindView(R.id.tv_wind_direction)
    TextView windDirectionTextView;
    @BindView(R.id.tv_wind_speed)
    TextView windSpeedTextView;
    @BindView(R.id.tv_condition)
    TextView conditionTextView;

    @Override
    protected void onViewModelAttach() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
        long time = getArguments().getLong(ARG_TIME);
        viewModel.showWeatherFor(time);
    }

    @Override
    protected void attachInputListeners() {
        viewModel.observe(this, this::showLoading, this::showError, this::showWeatherDetails);
    }


    private void showLoading(boolean loading) {
        if (loading)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private void showError(Throwable error) {
        errorTextView.setText(error.getMessage());
        errorTextView.setVisibility(View.VISIBLE);

        // TODO handle known errors
        throw new RuntimeException(error);
    }

    private void showWeatherDetails(UIWeatherDetail weather) {
        conditionTextView.setText(weather.getCondition().getFriendlyName());
        tempMaxTextView.setText(UIUtils.getFormattedTemperature(getContext(), weather.getTempMax()));
        tempMinTextView.setText(UIUtils.getFormattedTemperature(getContext(), weather.getTempMin()));
        humidityTextView.setText(UIUtils.getFormattedHumidity(getContext(), weather.getHumidity()));
        pressureTextView.setText(UIUtils.getFormattedPressure(getContext(), weather.getPressure()));
        windDirectionTextView.setText(weather.getWindDirection().getFriendlyName());
        windSpeedTextView.setText(UIUtils.getFormattedWindSpeed(getContext(), weather.getWindSpeed()));
        windDirectionImageView.setRotation(weather.getWindDirection().getRotation());

        Glide.with(getContext())
                .load(weather.getCondition().getConditionImage())
                .into(conditionImageView);
    }
}
