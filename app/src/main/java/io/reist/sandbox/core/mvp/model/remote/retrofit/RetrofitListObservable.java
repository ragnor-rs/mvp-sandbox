package io.reist.sandbox.core.mvp.model.remote.retrofit;

import java.io.IOException;
import java.util.List;

import io.reist.sandbox.core.mvp.model.ListObservable;
import retrofit.Call;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class RetrofitListObservable<I> extends ListObservable<I> {

    @Override
    protected Integer put(List<I> list) {
        try {
            return getWriteCall().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Call<Integer> getWriteCall();

    @Override
    protected List<I> get() {
        try {
            return getReadCall().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Call<List<I>> getReadCall();

}
