package io.reist.sandbox.core.model;

import android.util.Log;

import java.util.List;

/**
 * Created by Reist on 10/17/15.
 */
public abstract class CachedService<R, S extends EntityService<R>> implements EntityService<R> {

    private static final String TAG = CachedService.class.getName();

    protected final S localService;
    protected final S remoteService;

    protected CachedService(S localService, S remoteService) {
        this.localService = localService;
        this.remoteService = remoteService;
    }

    protected void enqueueReadListRequests(
            Observable<List<R>> localRequest,
            final Observable<List<R>> remoteRequest,
            final Observer<List<R>> response
    ) {

        localRequest.subscribe(new Observer<List<R>>() {

            @Override
            public void onNext(final List<R> t) {

                if (t == null || t.isEmpty()) {
                    remoteRequest.subscribe(new Observer<List<R>>() {

                        @Override
                        public void onNext(final List<R> t) {
                            chain(
                                    t,
                                    localService.storeList(t),
                                    response,
                                    "Wrote objects to cache: %s",
                                    "Error occurred"
                            );
                        }

                        @Override
                        public void onError(Throwable e) {
                            response.onError(e);
                        }

                    });
                } else {
                    response.onNext(t);
                }


            }

            @Override
            public void onError(Throwable e) {
                response.onError(e);
            }

        });

    }

    private static <I, O> void chain(
            final I inputData,
            Observable<O> outputRequest,
            final Observer<I> response,
            final String successLogMessage,
            final String errorLogMessage
    ) {

        outputRequest.subscribe(new Observer<O>() {

            @Override
            public void onNext(O t) {
                if (successLogMessage != null) {
                    Log.i(TAG, String.format(successLogMessage, t.toString()));
                }
                response.onNext(inputData);
            }

            @Override
            public void onError(Throwable e) {
                if (errorLogMessage != null) {
                    Log.e(TAG, errorLogMessage, e);
                }
                response.onError(e);
            }

        });

    }

    /*
    private static <I, O> void chain(
            Observable<I> inputRequest,
            final Observable<I> acceptorRequest,
            final Observable<O> outputRequest,
            final Observer<I> response,
            final String successLogMessage,
            final String errorLogMessage
    ) {

        inputRequest.subscribe(new Observer<I>() {

            @Override
            public void onNext(final I inputData) {
                acceptorRequest.subscribe(new Observer<I>() {

                    @Override
                    public void onNext(I inputData) {
                        Log.d(TAG, "Result accepted: " + inputData.toString());
                        response.onNext(inputData);
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Result discarded: " + inputData.toString(), error);
                        outputRequest.subscribe(new Observer<O>() {

                            @Override
                            public void onNext(O result) {
                                if (successLogMessage != null) {
                                    Log.i(TAG, String.format(successLogMessage, result.toString()));
                                }
                                response.onNext(inputData);
                            }

                            @Override
                            public void onError(Throwable error) {
                                if (errorLogMessage != null) {
                                    Log.e(TAG, errorLogMessage, error);
                                }
                                response.onError(error);
                            }

                        });
                    }

                });
            }

            @Override
            public void onError(Throwable error) {
                response.onError(error);
            }

        });

    }
    */

    @Override
    public Observable<Boolean> store(R data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Integer> storeList(final List<R> data) {
        throw new UnsupportedOperationException();
    }

}
