package com.ringov.yamblzweather.presentation.ui.about;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ringov.yamblzweather.BuildConfig;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by ringov on 07.07.17.
 */

public class AboutFragment extends BaseFragment {

    public static final String TAG = "about";

    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.about);

        mTvVersion.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
    }

    @Override
    protected int getLayout() {
        return R.layout.about_fragment;
    }
}
