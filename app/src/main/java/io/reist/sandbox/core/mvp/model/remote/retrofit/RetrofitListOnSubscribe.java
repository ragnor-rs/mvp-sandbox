package io.reist.sandbox.core.mvp.model.remote.retrofit;

import java.io.IOException;
import java.util.List;

import io.reist.sandbox.core.rx.Observable;
import io.reist.sandbox.core.rx.Subscriber;
import retrofit.Call;

/**
 * Created by Reist on 10/23/15.
 */
public abstract class RetrofitListOnSubscribe<I> implements Observable.OnSubscribe<List<I>> {

    @Override
    public void call(Subscriber<List<I>> listSubscriber) {
        try {
            listSubscriber.onNext(getReadCall().execute().body());
        } catch (IOException e) {
            listSubscriber.onError(e);
        }
    }

    protected abstract Call<List<I>> getReadCall();

}
