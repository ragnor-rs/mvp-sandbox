package io.reist.sandbox.core.mvp.presenter;

import android.content.Context;

import io.reist.sandbox.core.mvp.view.BaseView;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Reist on 10/15/15.
 */
public abstract class BasePresenter {

    protected CompositeSubscription subscriptions = new CompositeSubscription();
    private BaseView view;

    public final void setView(BaseView view) {
        this.view = view;
        if (view == null) {
            subscriptions.unsubscribe(); //cur not the best place to unsubscribe, right?
            onViewDetached();
        } else {
            onViewAttached(view);
        }
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
