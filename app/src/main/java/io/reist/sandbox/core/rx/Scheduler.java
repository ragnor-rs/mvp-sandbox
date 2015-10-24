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

    public abstract class Worker implements Subscription {

        public abstract Subscription schedule(Action0 action);

        @Override
        public void unsubscribe() {
            unregister(this);
        }
    }

}
