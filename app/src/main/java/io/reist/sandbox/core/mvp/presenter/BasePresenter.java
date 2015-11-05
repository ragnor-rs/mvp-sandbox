package io.reist.sandbox.core.mvp.presenter;

import android.content.Context;

import io.reist.sandbox.core.mvp.view.BaseView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Reist on 10/15/15.
 */
public abstract class BasePresenter {

    private CompositeSubscription subscriptions;
    private BaseView view;

    public final void setView(BaseView view) {
        this.view = view;
        if (view == null) {
            subscriptions.unsubscribe();
            onViewDetached();
        } else {
            subscriptions = new CompositeSubscription();
            onViewAttached(view);
        }
    }

    protected final <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        subscriptions.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer)
        );
    }

    protected abstract void onViewAttached(BaseView view);

    protected abstract void onViewDetached();

    public final BaseView getView() {
        return view;
    }

    public final Context getContext() {
        return view == null ? null : view.getContext();
    }

    public final Object getComponent() {
        return getView().getComponent();
    }

}
