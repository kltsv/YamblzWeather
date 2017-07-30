package com.ringov.yamblzweather.presentation.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ivan on 23.07.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable disposables = new CompositeDisposable();

    @LayoutRes
    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    // Subscribe for user input events in this method
    protected void attachInputListeners() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        attachInputListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposables.clear();
    }

    protected void replaceFragment(Fragment fragment, @IdRes int containerId) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(containerId, fragment)
                .commit();
    }
}
