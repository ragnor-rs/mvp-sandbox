package io.reist.sandbox.app;

import android.app.Application;

import io.reist.visum.ComponentCache;
import io.reist.visum.ComponentCacheProvider;

/**
 * Created by Reist on 10/16/15.
 */
public class SandboxApplication extends Application implements ComponentCacheProvider {

    private ComponentCache componentCache;

    @Override
    public ComponentCache getComponentCache() {
        if (componentCache == null) {
            componentCache = new SandboxComponentCache(this);
        }
        return componentCache;
    }

    public void setComponentCache(ComponentCache componentCache) {
        this.componentCache = componentCache;
    }

}
