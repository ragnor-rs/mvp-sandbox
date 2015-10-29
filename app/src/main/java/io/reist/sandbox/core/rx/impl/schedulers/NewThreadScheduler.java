package io.reist.sandbox.core.rx.impl.schedulers;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import io.reist.sandbox.core.rx.Action0;
import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Scheduler;
import io.reist.sandbox.core.rx.Subscription;

/**
 * Created by Reist on 10/24/15.
 */
public class NewThreadScheduler extends Scheduler {

    private static final String TAG = NewThreadScheduler.class.getName();

    @Override
    public Worker createWorker() {
        return register(new NewThreadWorker(this));
    }

    protected static class NewThreadWorker extends Worker {

        private final BlockingQueue<Action0> queue = new LinkedBlockingQueue<>();

        @Override
        public Subscription schedule(Action0 action) {
            queue.offer(action);
            return this;
        }

        public Thread thread = new Thread() {

            @Override
            public void run() {
                Log.e(TAG, "--- " + this + " STARTED  ---");
                Action0 action;
                try {
                    while ((action = queue.take()) != null) {
                        action.call();
                    }
                    Log.e(TAG, "--- " + this + " DIED ---");
                } catch (InterruptedException | Observable.StopThreadException e) {
                    Log.e(TAG, "--- " + this + " INTERRUPTED  ---");
                }
            }

        };

        protected NewThreadWorker(Scheduler scheduler) {
            super(scheduler);
            Log.e(TAG, "--- " + thread + " STARTING ---");
            thread.start();
        }

        @Override
        public void unsubscribe() {
            super.unsubscribe();
            Log.e(TAG, "--- " + thread + " STOPPING ---");
            thread.interrupt();
            thread = null;
        }

    }

}
