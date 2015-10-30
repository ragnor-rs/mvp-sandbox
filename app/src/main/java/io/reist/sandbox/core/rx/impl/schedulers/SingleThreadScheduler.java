package io.reist.sandbox.core.rx.impl.schedulers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import io.reist.sandbox.core.rx.Action0;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Scheduler;
import io.reist.sandbox.core.rx.Subscription;

import static io.reist.sandbox.core.rx.Observable.log;

/**
 * Created by Reist on 10/26/15.
 */
public class SingleThreadScheduler extends Scheduler {

    private final Worker worker;

    public SingleThreadScheduler() {
        this.worker = register(new ThreadWorker(this));
    }

    @Override
    public Worker createWorker() {
        return worker;
    }

    protected static class ThreadWorker extends Worker {

        private static final String TAG = ThreadWorker.class.getName();

        private final BlockingQueue<Action0> queue = new LinkedBlockingQueue<>();

        @Override
        public final Subscription schedule(Action0 action) {
            queue.offer(action);
            return this;
        }

        private Thread thread = new Thread() {

            @Override
            public void run() {
                log(TAG, "--- " + this + " STARTED  ---");
                try {
                    Action0 action;
                    while ((action = queue.take()) != null) {
                        action.call();
                    }
                    log(TAG, "--- " + this + " DIED ---");
                } catch (InterruptedException | Observable.StopThreadException e) {
                    log(TAG, "--- " + this + " INTERRUPTED  ---");
                }
            }

        };

        protected ThreadWorker(Scheduler scheduler) {
            super(scheduler);
            log(TAG, "--- " + thread + " STARTING ---");
            thread.start();
        }

        protected final void interrupt() {
            if (thread == null) {
                return;
            }
            log(TAG, "--- " + thread + " STOPPING ---");
            thread.interrupt();
            thread = null;
        }

    }


}
