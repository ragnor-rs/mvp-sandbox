package io.reist.sandbox.core.rx;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Reist on 10/23/15.
 */
public class AndroidSchedulers {

    public static Scheduler mainThread() {
        return new MainThreadScheduler();
    }

    private static class MainThreadScheduler implements Scheduler {

        private final Handler handler;

        public MainThreadScheduler() {
            handler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void post(final Action0 action) {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    action.call();
                }

            });
        }

    }

}
