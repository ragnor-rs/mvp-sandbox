package io.reist.sandbox.core.rx;

import io.reist.sandbox.core.rx.impl.schedulers.MainThreadScheduler;

/**
 * Created by Reist on 10/23/15.
 */
public class AndroidSchedulers {

    public static Scheduler mainThread() {
        return new MainThreadScheduler();
    }

}
