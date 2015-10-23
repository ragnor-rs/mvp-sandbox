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
            AsyncRequest<List<R>> localRequest,
            final AsyncRequest<List<R>> remoteRequest,
            final AsyncResponse<List<R>> response
    ) {

        localRequest.enqueue(new AsyncResponse<List<R>>() {

            @Override
            public void onSuccess(final List<R> result) {

                if (result == null || result.isEmpty()) {
                    remoteRequest.enqueue(new AsyncResponse<List<R>>() {

                        @Override
                        public void onSuccess(final List<R> data) {
                            chain(
                                    data,
                                    localService.storeList(data),
                                    response,
                                    "Wrote objects to cache: %s",
                                    "Error occurred"
                            );
                        }

                        @Override
                        public void onError(Throwable error) {
                            response.onError(error);
                        }

                    });
                } else {
                    response.onSuccess(result);
                }


            }

            @Override
            public void onError(Throwable error) {
                response.onError(error);
            }

        });

    }

    private static <I, O> void chain(
            final I inputData,
            AsyncRequest<O> outputRequest,
            final AsyncResponse<I> response,
            final String successLogMessage,
            final String errorLogMessage
    ) {

        outputRequest.enqueue(new AsyncResponse<O>() {

            @Override
            public void onSuccess(O result) {
                if (successLogMessage != null) {
                    Log.i(TAG, String.format(successLogMessage, result.toString()));
                }
                response.onSuccess(inputData);
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

    /*
    private static <I, O> void chain(
            AsyncRequest<I> inputRequest,
            final AsyncRequest<I> acceptorRequest,
            final AsyncRequest<O> outputRequest,
            final AsyncResponse<I> response,
            final String successLogMessage,
            final String errorLogMessage
    ) {

        inputRequest.enqueue(new AsyncResponse<I>() {

            @Override
            public void onSuccess(final I inputData) {
                acceptorRequest.enqueue(new AsyncResponse<I>() {

                    @Override
                    public void onSuccess(I inputData) {
                        Log.d(TAG, "Result accepted: " + inputData.toString());
                        response.onSuccess(inputData);
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Result discarded: " + inputData.toString(), error);
                        outputRequest.enqueue(new AsyncResponse<O>() {

                            @Override
                            public void onSuccess(O result) {
                                if (successLogMessage != null) {
                                    Log.i(TAG, String.format(successLogMessage, result.toString()));
                                }
                                response.onSuccess(inputData);
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
    public AsyncRequest<Boolean> store(R data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AsyncRequest<Integer> storeList(final List<R> data) {
        throw new UnsupportedOperationException();
    }

}
