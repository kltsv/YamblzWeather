package com.ivanantsiferov.yamblzweather;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Create test RxJava schedulers from this executor
 */
public class CurrentThreadExecutor implements Executor {

    public void execute(@NonNull Runnable runnable) {
        runnable.run();
    }
}
