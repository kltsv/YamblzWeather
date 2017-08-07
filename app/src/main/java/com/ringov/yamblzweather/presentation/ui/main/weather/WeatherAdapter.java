package com.ringov.yamblzweather.presentation.ui.main.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;
import com.ringov.yamblzweather.presentation.ui.UIUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherItemVH> {

    private static final int TODAY_TYPE = 0;
    private static final int LIST_TYPE = 1;

    private List<UIWeatherList> items;
    private Context context;

    private PublishSubject<UIWeatherList> onItemClickSubject = PublishSubject.create();
    public Observable<UIWeatherList> getOnItemClickObservable() {
        return onItemClickSubject;
    }

    private CompositeDisposable disposables = new CompositeDisposable();
    public void destroy() {
        disposables.clear();
    }

    public WeatherAdapter(Context context, List<UIWeatherList> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public WeatherItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TODAY_TYPE) {
            View view = inflater.inflate(R.layout.item_weather_today, parent, false);
            return new WeatherItemTodayVH(view);
        } else {
            View view = inflater.inflate(R.layout.item_weather, parent, false);
            return new WeatherItemVH(view);
        }
    }

    @Override
    public void onBindViewHolder(WeatherItemVH holder, int position) {
        UIWeatherList item = items.get(position);

        holder.dateTextView.setText(UIUtils.getFormattedTime(context, item.getTime()));
        float tempMax = UIUtils.kelvinToCelsius(item.getTempMax());
        holder.tempMaxTextView.setText(UIUtils.getFormattedTemperature(context, tempMax));
        float tempMin = UIUtils.kelvinToCelsius(item.getTempMin());
        holder.tempMinTextView.setText(UIUtils.getFormattedTemperature(context, tempMin));
        Glide.with(holder.itemView.getContext())
                .load(item.getCondition().getConditionImage())
                .into(holder.conditionImageView);

        if (holder instanceof WeatherItemTodayVH) {
            WeatherItemTodayVH h = (WeatherItemTodayVH) holder;
            h.conditionTextView.setText(item.getCondition().getFriendlyName());
        }

        disposables.add(
                RxView.clicks(holder.itemView).subscribe(o -> onItemClickSubject.onNext(item)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TODAY_TYPE;
        } else {
            return LIST_TYPE;
        }
    }

    public void replace(List<UIWeatherList> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
