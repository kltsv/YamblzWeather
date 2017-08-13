package com.ivanantsiferov.yamblzweather;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * For test Schedulers to get code executed synchronised
 */
public class CurrentThreadExecutor implements Executor {

    public void execute(@NonNull Runnable r) {
        r.run();
    }
}
