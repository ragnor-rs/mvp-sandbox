package io.reist.sandbox.core.rx.impl.schedulers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import io.reist.sandbox.core.rx.Action0;
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

        private final Object lock = new Object();

        private Looper looper;

        @Override
        public Subscription schedule(final Action0 action) {

            synchronized (lock) {
                while (handler == null) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Exception occurred", e);
                    }
                }
            }

            handler.post(new Runnable() {

                @Override
                public void run() {
                    action.call();
                }

            });

            return this;

        }

        public Thread thread = new Thread() {

            @Override
            public void run() {
                Looper.prepare();
                looper = Looper.myLooper();
                handler = new Handler();
                synchronized (lock) {
                    lock.notifyAll();
                }
                Looper.loop();
            }

        };

        private Handler handler;

        protected NewThreadWorker(Scheduler scheduler) {
            super(scheduler);
            thread.start();
        }

        @Override
        public void unsubscribe() {
            super.unsubscribe();
            if (looper != null) {
                looper.quit();
            }
            thread = null;
        }

    }

}
