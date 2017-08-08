package com.ringov.yamblzweather.presentation.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.entity.UICityFavorite;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {

    private List<UICityFavorite> items;

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
        //Context context = holder.itemView.getContext();
        UICityFavorite item = items.get(position);

        holder.nameTextView.setText(item.getCityName());

        if (item.isEnabled())
            holder.cityImageView.setImageResource(R.drawable.ic_chosen_city_black_24dp);
        else
            holder.cityImageView.setImageResource(R.drawable.ic_city_black_24dp);

        if (position == 0)
            holder.removeImageButton.setVisibility(View.GONE);
        else
            holder.removeImageButton.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    void replace(List<UICityFavorite> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }
}
