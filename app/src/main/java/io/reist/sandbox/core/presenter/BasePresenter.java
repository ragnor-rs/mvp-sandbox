package io.reist.sandbox.core.presenter;

import android.content.Context;

import io.reist.sandbox.core.view.BaseView;

/**
 * Created by Reist on 10/15/15.
 */
public abstract class BasePresenter {

    private BaseView view;

    public final void setView(BaseView view) {
        this.view = view;
    }

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
