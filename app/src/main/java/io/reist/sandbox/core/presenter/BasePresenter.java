package io.reist.sandbox.core.presenter;

import android.content.Context;

import io.reist.sandbox.core.view.BaseView;

/**
 * Created by Reist on 10/15/15.
 */
public abstract class BasePresenter<V extends BaseView> {

    private V view;

    public final V attach(V view) {
        this.view = view;
        return view;
    }

    public final void detach() {
        this.view = null;
    }

    public final V getView() {
        return view;
    }

    public final Context getContext() {
        return view == null ? null : view.getContext();
    }

    public abstract void update();

}
