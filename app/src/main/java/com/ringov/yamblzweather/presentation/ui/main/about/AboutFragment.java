package com.ringov.yamblzweather.presentation.ui.main.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ringov.yamblzweather.BuildConfig;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseFragment;

import butterknife.BindView;

public class AboutFragment extends BaseFragment {

    public static final String TAG = "AboutFragment";

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_about;
    }

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.about_title);

        tvVersion.setText(getString(R.string.about_version, BuildConfig.VERSION_NAME));
    }
}
