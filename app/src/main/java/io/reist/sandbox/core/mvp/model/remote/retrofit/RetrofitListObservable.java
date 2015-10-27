package io.reist.sandbox.core.mvp.model.remote.retrofit;

import java.io.IOException;
import java.util.List;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;
import retrofit.Call;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class RetrofitListObservable<I> extends Observable<List<I>> {

    public RetrofitListObservable() {
        super(null);
    }

    //@Override
    public Func0<List<I>> getEmittingFunction() {
        return new Func0<List<I>>() {

            @Override
            public List<I> call() {
                try {
                    return getReadCall().execute().body();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        };
    }

    protected abstract Call<List<I>> getReadCall();

}
