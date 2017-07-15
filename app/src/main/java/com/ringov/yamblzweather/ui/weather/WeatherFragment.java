package com.ringov.yamblzweather.ui.weather;

import android.widget.TextView;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.viewmodel.model.WeatherInfo;
import com.ringov.yamblzweather.viewmodel.weather.WeatherViewModel;
import com.ringov.yamblzweather.ui.base.ModelViewFragment;

import butterknife.BindView;

/**
 * Created by ringov on 07.07.17.
 */

public class WeatherFragment extends ModelViewFragment<WeatherViewModel, WeatherInfo> {

    public static final String TAG = "weather";

	@BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_conditions)
    TextView tvConditions;

    @Override
    protected int getLayout() {
        return R.layout.weather_fragment;
    }

    @Override
    protected Class<WeatherViewModel> getViewModelClass() {
        return WeatherViewModel.class;
    }

    @Override
    protected void showDataChanges(WeatherInfo data) {
        tvTemperature.setText(data.getTemperature() + "");
        tvConditions.setText(data.getConditions());
    }
}
