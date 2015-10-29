package io.reist.sandbox.core.rx.impl.schedulers;

import io.reist.sandbox.core.rx.Action0;
import io.reist.sandbox.core.rx.Scheduler;
import io.reist.sandbox.core.rx.Subscription;

/**
 * Created by Reist on 10/24/15.
 */
public class ImmediateScheduler extends Scheduler {

    @Override
    public Worker createWorker() {
        return register(new ImmediateWorker(this));
    }

    private class ImmediateWorker extends Worker {

        public ImmediateWorker(Scheduler parent) {
            super(parent);
        }

        @Override
        public Subscription schedule(Action0 action) {
            action.call();
            return this;
        }

    }

}
