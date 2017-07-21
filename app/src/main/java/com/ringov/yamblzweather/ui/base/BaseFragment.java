package com.ringov.yamblzweather.ui.base;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by ringov on 07.07.17.
 */

public abstract class BaseFragment extends LifecycleFragment {

    @LayoutRes
    protected abstract int getLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
