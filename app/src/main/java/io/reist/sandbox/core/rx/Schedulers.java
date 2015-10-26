package io.reist.sandbox.core.rx;

import io.reist.sandbox.core.rx.impl.ImmediateScheduler;
import io.reist.sandbox.core.rx.impl.IoThreadScheduler;
import io.reist.sandbox.core.rx.impl.NewThreadScheduler;

/**
 * Created by Reist on 10/23/15.
 */
public class Schedulers {

    public static final ImmediateScheduler IMMEDIATE_SCHEDULER = new ImmediateScheduler();
    public static final NewThreadScheduler NEW_THREAD_SCHEDULER = new NewThreadScheduler();
    public static final Scheduler IO_SCHEDULER = new IoThreadScheduler();

    public static Scheduler immediate() {
        return IMMEDIATE_SCHEDULER;
    }

    public static Scheduler newThread() {
        return NEW_THREAD_SCHEDULER;
    }

    public static Scheduler io() {
        return IO_SCHEDULER;
    }

}
