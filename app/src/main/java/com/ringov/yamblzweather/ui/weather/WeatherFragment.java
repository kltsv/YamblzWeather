package com.ringov.yamblzweather.ui.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.ui.base.ModelViewFragment;
import com.ringov.yamblzweather.presenter.model.WeatherInfo;
import com.ringov.yamblzweather.presenter.weather.WeatherViewModel;

import butterknife.BindView;

/**
 * Created by ringov on 07.07.17.
 */

public class WeatherFragment extends ModelViewFragment<WeatherViewModel, WeatherInfo> {

	@BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_conditions)
    TextView tvConditions;

    @Override
    protected int getLayout() {
        return R.layout.weather_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
