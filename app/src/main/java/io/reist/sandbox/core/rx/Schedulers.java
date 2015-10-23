package io.reist.sandbox.core.rx;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by Reist on 10/23/15.
 */
public class Schedulers {

    public static Scheduler immediate() {
        return new ImmediateScheduler();
    }

    public static Scheduler newThread() {
        return new ThreadScheduler();
    }

    private static class ThreadScheduler extends Thread implements Scheduler {

        private static final String TAG = ThreadScheduler.class.getName();

        private final Object lock = new Object();

        private Handler handler;

        private ThreadScheduler() {
            start();
        }

        @Override
        public void post(final Action0 action) {

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

        }

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler();
            synchronized (lock) {
                lock.notifyAll();
            }
            Looper.loop();
        }

    }

    private static class ImmediateScheduler implements Scheduler {

        @Override
        public void post(Action0 action) {
            action.call();
        }

    }

}
