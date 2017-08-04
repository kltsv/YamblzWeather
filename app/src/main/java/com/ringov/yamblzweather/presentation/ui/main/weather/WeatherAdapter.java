package com.ringov.yamblzweather.presentation.ui.main.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;
import com.ringov.yamblzweather.presentation.ui.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherItemVH> {

    private List<UIWeatherList> items;
    private Context context;

    public WeatherAdapter(Context context, List<UIWeatherList> items) {
        this.context = context;
        this.items = items;
    }

    public static class WeatherItemVH extends RecyclerView.ViewHolder {

        @BindView(R.id.im_condition)
        ImageView conditionImageView;
        @BindView(R.id.tv_date)
        TextView dateTextView;
        @BindView(R.id.tv_temperature)
        TextView temperatureTextView;

        public WeatherItemVH(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public WeatherItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_weather, parent, false);
        return new WeatherItemVH(view);
    }

    @Override
    public void onBindViewHolder(WeatherItemVH holder, int position) {
        UIWeatherList item = items.get(position);

        holder.dateTextView.setText(UIUtils.getRelativeTime(context, item.getTime()));
        holder.temperatureTextView.setText(UIUtils.getFormattedTemperature(context, item.getTemperature()));
        Glide.with(holder.itemView.getContext())
                .load(item.getCondition().getConditionImage())
                .into(holder.conditionImageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
