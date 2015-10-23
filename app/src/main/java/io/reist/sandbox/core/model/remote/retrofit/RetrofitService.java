package io.reist.sandbox.core.model.remote.retrofit;

import android.util.Log;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.AsyncResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Reist on 10/16/15.
 */
public abstract class RetrofitService<R> {

    private static final String TAG = RetrofitService.class.getName();

    public AsyncRequest<R> createRequest(final Call<R> call) {
        return new AsyncRequest<R>() {

            @Override
            public void enqueue(final AsyncResponse<R> asyncResponse) {
                call.enqueue(new Callback<R>() {

                    @Override
                    public void onResponse(Response<R> response, Retrofit retrofit) {
                        asyncResponse.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "Exception occurred", t);
                        asyncResponse.onError(t);
                    }

                });
            }

        };
    }

    public AsyncRequest<List<R>> createListRequest(final Call<List<R>> call) {
        return new AsyncRequest<List<R>>() {

            @Override
            public void enqueue(final AsyncResponse<List<R>> asyncResponse) {
                call.enqueue(new Callback<List<R>>() {

                    @Override
                    public void onResponse(Response<List<R>> response, Retrofit retrofit) {
                        asyncResponse.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "Exception occurred", t);
                        asyncResponse.onError(t);
                    }

                });
            }

        };
    }

}
