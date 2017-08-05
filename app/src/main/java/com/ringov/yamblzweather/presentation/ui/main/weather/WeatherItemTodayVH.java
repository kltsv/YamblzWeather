package com.ringov.yamblzweather.presentation.ui.main.weather;

import android.view.View;
import android.widget.TextView;

import com.ringov.yamblzweather.R;

import butterknife.BindView;

public class WeatherItemTodayVH extends WeatherItemVH {

    @BindView(R.id.tv_condition)
    TextView conditionTextView;

    public WeatherItemTodayVH(final View itemView) {
        super(itemView);
    }
}
