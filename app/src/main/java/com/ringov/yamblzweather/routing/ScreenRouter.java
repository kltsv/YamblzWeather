package com.ringov.yamblzweather.routing;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;

/**
 * Created by ringov on 07.07.17.
 */

public class ScreenRouter {

    @IdRes
    private int container;
    private FragmentManager manager;

    public ScreenRouter(FragmentManager manager, @IdRes int container) {
        this.manager = manager;
        this.container = container;
    }

    public void open(Screen screen) {
        if (container != 0) {
            screen.open(manager, container);
        }
    }

    public void detach() {
        manager = null;
    }
}
