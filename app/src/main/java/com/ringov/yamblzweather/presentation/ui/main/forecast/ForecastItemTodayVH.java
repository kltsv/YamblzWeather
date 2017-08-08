package com.ringov.yamblzweather.presentation.ui.main.forecast;

import android.view.View;
import android.widget.TextView;

import com.ringov.yamblzweather.R;

import butterknife.BindView;

public class ForecastItemTodayVH extends ForecastItemVH {

    @BindView(R.id.tv_condition)
    TextView conditionTextView;

    public ForecastItemTodayVH(final View itemView) {
        super(itemView);
    }
}
