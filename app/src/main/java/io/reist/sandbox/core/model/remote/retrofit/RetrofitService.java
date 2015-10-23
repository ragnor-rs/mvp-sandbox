package io.reist.sandbox.core.model.remote.retrofit;

import android.util.Log;

import java.util.List;

import io.reist.sandbox.core.model.Observable;
import io.reist.sandbox.core.model.Observer;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Reist on 10/16/15.
 */
public abstract class RetrofitService<R> {

    private static final String TAG = RetrofitService.class.getName();

    public Observable<R> toObservable(final Call<R> call) {
        return new Observable<R>() {

            @Override
            public void subscribe(final Observer<R> observer) {
                call.enqueue(new Callback<R>() {

                    @Override
                    public void onResponse(Response<R> response, Retrofit retrofit) {
                        observer.onNext(response.body());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "Exception occurred", t);
                        observer.onError(t);
                    }

                });
            }

        };
    }

    public Observable<List<R>> toListObservable(final Call<List<R>> call) {
        return new Observable<List<R>>() {

            @Override
            public void subscribe(final Observer<List<R>> observer) {
                call.enqueue(new Callback<List<R>>() {

                    @Override
                    public void onResponse(Response<List<R>> response, Retrofit retrofit) {
                        observer.onNext(response.body());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "Exception occurred", t);
                        observer.onError(t);
                    }

                });
            }

        };
    }

}
