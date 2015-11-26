package io.reist.sandbox.app.model;

import io.reist.visum.model.Error;
import io.reist.visum.model.Response;
import io.reist.visum.model.VisumError;
import rx.Observer;

/**
 * Created by defuera on 12/11/2015.
 */
public abstract class ResponseObserver<T> implements Observer<Response<T>> {

    @Override
    public void onNext(Response<T> response) {
        if (response.isSuccessful())
            onSuccess(response.getResult());
        else
            onFail(response.getError());
    }

    protected abstract void onFail(Error error);

    protected abstract void onSuccess(T result);

    @Override
    public void onError(Throwable e) {
        VisumError error = new VisumError();

        error.setThrowable(e);

        onFail(error);
    }

    @Override
    public void onCompleted() {}

}
