package io.reist.sandbox.core.rx;

import io.reist.sandbox.core.rx.impl.schedulers.ImmediateScheduler;
import io.reist.sandbox.core.rx.impl.schedulers.NewThreadScheduler;
import io.reist.sandbox.core.rx.impl.schedulers.SingleThreadScheduler;

/**
 * Created by Reist on 10/23/15.
 */
public class Schedulers {

    public static final ImmediateScheduler IMMEDIATE_SCHEDULER = new ImmediateScheduler();
    public static final NewThreadScheduler NEW_THREAD_SCHEDULER = new NewThreadScheduler();
    public static final Scheduler IO_SCHEDULER = new SingleThreadScheduler();
    public static final Scheduler COMPUTATION_SCHEDULER = new SingleThreadScheduler();

    public static Scheduler immediate() {
        return IMMEDIATE_SCHEDULER;
    }

    public static Scheduler newThread() {
        return NEW_THREAD_SCHEDULER;
    }

    public static Scheduler io() {
        return IO_SCHEDULER;
    }

    public static Scheduler computation() {
        return COMPUTATION_SCHEDULER;
    }

}
