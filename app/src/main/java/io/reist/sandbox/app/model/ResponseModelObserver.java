package io.reist.sandbox.app.model;

import rx.Observer;

/**
 * Created by defuera on 12/11/2015.
 */
public abstract class ResponseModelObserver<T> implements Observer<ResponseModel<T>> {

    @Override
    public void onNext(ResponseModel<T> response) {
        if (response.isSuccessful())
            onSuccess(response.getData());
        else
            onFail(response.getError());
    }

    protected abstract void onFail(ResponseModel.Error error);

    protected abstract void onSuccess(T data);

    @Override
    public void onError(Throwable e) {
        onFail(new ResponseModel.Error(e.getMessage()));
    }

    @Override
    public void onCompleted() {

    }

}
