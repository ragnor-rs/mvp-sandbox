package io.reist.sandbox.core.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by Reist on 10/16/15.
 */
public abstract class BackgroundService extends Thread {

    private static final String TAG = BackgroundService.class.getName();

    private final Object lock = new Object();

    private Handler backgroundThreadHandler;

    private Handler mainThreadHandler;

    public BackgroundService() {
        mainThreadHandler = new Handler(Looper.getMainLooper());
        start();
    }

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        synchronized (lock) {
           backgroundThreadHandler = new Handler();
           lock.notifyAll();
        }
        Looper.loop();
    }

    private <R> void reportSuccess(final AsyncResponse<R> response, final R result) {
        mainThreadHandler.post(new Runnable() {

            @Override
            public void run() {
                response.onSuccess(result);
            }

        });
    }

    private <R> void reportFailure(final AsyncResponse<R> response, final Throwable error) {
        mainThreadHandler.post(new Runnable() {

            @Override
            public void run() {
                response.onError(error);
            }

        });
    }

    public <R> AsyncRequest<R> createRequest(final BackgroundOp<R> call) {
        return new AsyncRequest<R>() {

            @Override
            public void enqueue(final AsyncResponse<R> response) {

                synchronized (lock) {
                    while (backgroundThreadHandler == null) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Log.e(TAG, "Exception occurred", e);
                        }
                    }
                }

                backgroundThreadHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            reportSuccess(response, call.execute());
                        } catch (Throwable e) {
                            reportFailure(response, e);
                        }
                    }

                });

            }

        };
    }

}
