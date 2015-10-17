package io.reist.sandbox.core.model;

import android.util.Log;

import java.util.List;

import io.reist.sandbox.core.model.local.LocalService;

/**
 * Created by Reist on 10/17/15.
 */
public abstract class CachedService<R, LS extends LocalService<R>> {

    private static final String TAG = CachedService.class.getName();

    private final LS localService;

    protected CachedService(LS localService) {
        this.localService = localService;
    }

    protected void enqueueLocalReadRequest(AsyncRequest<R> request, final AsyncRequest<R> remoteReadRequest, final AsyncResponse<R> response) {
        request.enqueue(new AsyncResponse<R>() {

            @Override
            public void onSuccess(R result) {
                if (result == null) {
                    enqueueRemoteReadRequest(remoteReadRequest, response);
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

    protected void enqueueLocalReadListRequest(AsyncRequest<List<R>> request, final AsyncRequest<List<R>> remoteReadRequest, final AsyncResponse<List<R>> response) {
        request.enqueue(new AsyncResponse<List<R>>() {

            @Override
            public void onSuccess(List<R> result) {
                if (result == null || result.isEmpty()) {
                    enqueueRemoteReadListRequest(remoteReadRequest, response);
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

    protected void enqueueRemoteReadRequest(AsyncRequest<R> request, final AsyncResponse<R> response) {
        request.enqueue(new AsyncResponse<R>() {

            @Override
            public void onSuccess(R result) {
                enqueueLocalWriteRequest(result);
                response.onSuccess(result);
            }

            @Override
            public void onError(Throwable error) {
                response.onError(error);
            }

        });
    }

    protected void enqueueRemoteReadListRequest(AsyncRequest<List<R>> request, final AsyncResponse<List<R>> response) {
        request.enqueue(new AsyncResponse<List<R>>() {

            @Override
            public void onSuccess(List<R> result) {
                enqueueLocalWriteListRequest(result);
                response.onSuccess(result);
            }

            @Override
            public void onError(Throwable error) {
                response.onError(error);
            }

        });
    }

    protected void enqueueLocalWriteRequest(R data) {
        localService.store(data).enqueue(new AsyncResponse<Boolean>() {

            @Override
            public void onSuccess(Boolean result) {
                Log.i(TAG, "Wrote an object to cache: " + result.toString());
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "Error writing to cache", error);
            }

        });
    }

    protected void enqueueLocalWriteListRequest(List<R> data) {
        localService.storeList(data).enqueue(new AsyncResponse<Integer>() {

            @Override
            public void onSuccess(Integer result) {
                Log.i(TAG, "Wrote objects to cache: " + result.toString());
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "Error writing to cache", error);
            }

        });
    }

    public LS getLocalService() {
        return localService;
    }

}
