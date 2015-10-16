package io.reist.sandbox.core.model.network;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.AsyncResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Reist on 10/16/15.
 */
public abstract class RetrofitService {

    public <R> AsyncRequest<R> createRequest(final Call<R> call) {
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
                        asyncResponse.onError(t);
                    }

                });
            }

        };
    }

}
