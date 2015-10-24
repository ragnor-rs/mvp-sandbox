package io.reist.sandbox.core.rx.impl;

import android.os.Handler;
import android.os.Looper;

import io.reist.sandbox.core.rx.Action0;
import io.reist.sandbox.core.rx.Scheduler;
import io.reist.sandbox.core.rx.Subscription;

/**
 * Created by Reist on 10/24/15.
 */
public class MainThreadScheduler extends Scheduler {

    @Override
    public Worker createWorker() {
        return register(new MainThreadWorker());
    }

    private class MainThreadWorker extends Worker {

        private final Handler handler;

        public MainThreadWorker() {
            handler = new Handler(Looper.getMainLooper());
        }

        @Override
        public Subscription schedule(final Action0 action) {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    action.call();
                }

            });
            return this;
        }

    }

}
