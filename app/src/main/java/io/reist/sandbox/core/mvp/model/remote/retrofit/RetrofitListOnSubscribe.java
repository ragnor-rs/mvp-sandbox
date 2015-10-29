package io.reist.sandbox.core.mvp.model.remote.retrofit;

import java.io.IOException;
import java.util.List;

import io.reist.sandbox.core.rx.impl.origins.OriginOnSubscribe;
import retrofit.Call;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class RetrofitListOnSubscribe<I> extends OriginOnSubscribe<List<I>> {

    @Override
    public List<I> call() throws IOException {
        return getReadCall().execute().body();
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    protected abstract Call<List<I>> getReadCall();

}
