package io.reist.sandbox.app.model;

import io.reist.visum.Error;
import io.reist.visum.model.Response;
import rx.Observer;

/**
 * Created by defuera on 12/11/2015.
 */
public abstract class ResponseObserver<T> implements Observer<Response<T>> {

    @Override
    public void onNext(Response<T> response) {
        if (response.isSuccessful())
            onSuccess(response.getData());
        else
            onFail(response.getError());
    }

    protected abstract void onFail(io.reist.visum.Error error);

    protected abstract void onSuccess(T data);

    @Override
    public void onError(Throwable e) {
        onFail(new Error(e.getMessage()));
    }

    @Override
    public void onCompleted() {

    }

}
