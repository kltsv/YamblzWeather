package com.ringov.yamblzweather.presentation.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ringov.yamblzweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.im_city)
    ImageView cityImageView;
    @BindView(R.id.tv_city_name)
    TextView nameTextView;
    @BindView(R.id.ib_remove_city)
    ImageButton removeImageButton;

    public CityViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
