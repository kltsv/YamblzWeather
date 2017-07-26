package com.ringov.yamblzweather.presentation.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.navigation.base.Command;
import com.ringov.yamblzweather.navigation.base.Navigator;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 23.07.2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements DisposablesHolder, Navigator {

    @LayoutRes
    protected abstract int getLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.getNavigationBinder().setNavigator(this);
    }

    @Override
    public boolean executeCommand(Command command) {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.getNavigationBinder().removeNavigator();
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

    protected void replaceFragment(Fragment fragment, @IdRes int containerId) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(containerId, fragment)
                .commit();
    }
}
