package com.ringov.yamblzweather.presentation.ui.splash;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.data.database.AppDatabaseCreator;
import com.ringov.yamblzweather.presentation.base.BaseActivity;
import com.ringov.yamblzweather.presentation.ui.main.MainActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements LifecycleRegistryOwner {

    @BindView(R.id.im_logo)
    ImageView logoImageView;

    RotateAnimation rotateAnimation;

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(null);

        AppDatabaseCreator.getInstance().isDatabaseCreated().observe(this, this::onDatabaseLoaded);
    }

    @Override
    protected void onResume() {
        super.onResume();

        rotateAnimation = new RotateAnimation(
                0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(5000);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        logoImageView.startAnimation(rotateAnimation);
    }

    @Override
    protected void onStop() {
        super.onStop();

        logoImageView.animate().cancel();
    }

    private void onDatabaseLoaded(boolean loaded) {
        if (loaded) {
            // Build dagger module after DB initialization to avoid NPE
            App app = (App) getApplication();
            app.initDagger();
            // Open main and finish splash activity
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }
}
