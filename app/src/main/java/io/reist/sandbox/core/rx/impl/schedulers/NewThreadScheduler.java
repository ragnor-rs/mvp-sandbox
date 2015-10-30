package io.reist.sandbox.core.rx.impl.schedulers;

import io.reist.sandbox.core.rx.Scheduler;

/**
 * Created by Reist on 10/24/15.
 */
public class NewThreadScheduler extends Scheduler {

    private static final String TAG = NewThreadScheduler.class.getName();

    @Override
    public Worker createWorker() {
        return register(new NewThreadWorker(this));
    }

    protected static class NewThreadWorker extends SingleThreadScheduler.ThreadWorker {

        protected NewThreadWorker(Scheduler scheduler) {
            super(scheduler);
        }

        @Override
        public synchronized void unsubscribe() {
            super.unsubscribe();
            interrupt();
        }

    }

}
