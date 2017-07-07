package com.ringov.yamblzweather.routing;

import android.support.v4.app.Fragment;

/**
 * Created by ringov on 07.07.17.
 */

public interface ScreenView<F extends Fragment> {
    F getFragment();
}
