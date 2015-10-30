package io.reist.sandbox.core.rx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class Scheduler {

    private final List<Worker> workers = new ArrayList<>();

    public abstract Worker createWorker();

    protected Worker register(Worker worker) {
        workers.add(worker);
        return worker;
    }

    protected void unregister(Worker worker) {
        workers.remove(worker);
    }

    public static abstract class Worker implements Subscription {

        private final Scheduler parent;

        public Worker(Scheduler parent) {
            this.parent = parent;
        }

        public abstract Subscription schedule(Action0 action);

        @Override
        public synchronized void unsubscribe() {
            parent.unregister(this);
        }

    }

}
