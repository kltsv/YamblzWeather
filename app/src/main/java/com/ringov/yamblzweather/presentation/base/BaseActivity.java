package com.ringov.yamblzweather.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 23.07.2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements DisposablesHolder {

    @LayoutRes
    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
