package io.reist.sandbox.core.presenter;

import android.content.Context;

import io.reist.sandbox.core.view.BaseView;

/**
 * Created by Reist on 10/15/15.
 */
public abstract class BasePresenter<V extends BaseView> {

    private V view;

    public final void setView(V view) {
        this.view = view;
    }

    public final V getView() {
        return view;
    }

    public final Context getContext() {
        return view == null ? null : view.getContext();
    }

}
