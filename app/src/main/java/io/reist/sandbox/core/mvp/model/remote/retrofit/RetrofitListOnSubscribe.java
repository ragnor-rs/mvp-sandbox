package io.reist.sandbox.core.mvp.model.remote.retrofit;

import java.io.IOException;
import java.util.List;

import io.reist.sandbox.core.rx.impl.AbstractOnSubscribe;
import retrofit.Call;
import rx.functions.Func0;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class RetrofitListOnSubscribe<I> extends AbstractOnSubscribe<List<I>> implements Func0<List<I>> {

    @Override
    protected final void emit() throws Exception {
        doOnNext(call());
    }

    @Override
    public final List<I> call() {
        try {
            return getReadCall().execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Call<List<I>> getReadCall();

}
