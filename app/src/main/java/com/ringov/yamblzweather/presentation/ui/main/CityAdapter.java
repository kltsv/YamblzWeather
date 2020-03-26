package com.ringov.yamblzweather.presentation.ui.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.entity.UICityFavorite;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {

    private List<UICityFavorite> items;

    // Simple on item click
    private PublishSubject<UICityFavorite> onItemClickSubject = PublishSubject.create();

    Observable<UICityFavorite> getOnItemClickObservable() {
        return onItemClickSubject;
    }

    // On item remove button click
    private PublishSubject<UICityFavorite> onItemRemoveClickSubject = PublishSubject.create();

    Observable<UICityFavorite> getOnItemRemoveClickObservable() {
        return onItemRemoveClickSubject;
    }

    private CompositeDisposable disposables = new CompositeDisposable();

    void destroy() {
        disposables.clear();
    }

    CityAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        UICityFavorite item = items.get(position);

        holder.nameTextView.setText(item.getCityName());

        if (item.isEnabled()) {
            holder.cityImageView.setImageResource(R.drawable.ic_chosen_city_24dp);
            holder.nameTextView.setTextColor(ContextCompat.getColor(context, R.color.color_accent_secondary));
        } else {
            holder.cityImageView.setImageResource(R.drawable.ic_city_24dp);
            holder.nameTextView.setTextColor(ContextCompat.getColor(context, R.color.color_text_primary));
        }

        if (items.size() == 1) {
            holder.removeImageButton.setVisibility(View.GONE);
        } else {
            holder.removeImageButton.setVisibility(View.VISIBLE);

            disposables.add(
                    RxView.clicks(holder.removeImageButton)
                            .subscribe(o -> onItemRemoveClickSubject.onNext(item)));
        }

        disposables.add(
                RxView.clicks(holder.itemView).subscribe(o -> onItemClickSubject.onNext(item)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void replace(List<UICityFavorite> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }
}
