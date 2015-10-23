package io.reist.sandbox.core;

import android.app.Application;

import io.reist.sandbox.core.di.ComponentCache;
import io.reist.sandbox.core.mvp.view.BaseView;

/**
 * Created by Reist on 10/16/15.
 */
public abstract class BaseApplication extends Application {

    private final ComponentCache componentCache = new ComponentCache(this);

    public ComponentCache getComponentCache() {
        return componentCache;
    }

    public abstract Object buildComponentFor(BaseView view);

}
