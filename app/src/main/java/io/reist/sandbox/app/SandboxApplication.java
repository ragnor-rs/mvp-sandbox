package io.reist.sandbox.app;

import android.content.Context;

import io.reist.sandbox.app.di.DaggerSandboxComponent;
import io.reist.sandbox.app.di.SandboxComponent;
import io.reist.sandbox.app.di.SandboxModule;
import io.reist.sandbox.core.BaseApplication;
import io.reist.sandbox.core.di.BaseModule;
import io.reist.sandbox.core.di.BaseViewModule;
import io.reist.sandbox.core.mvp.view.BaseView;
import io.reist.sandbox.repos.mvp.view.RepoListFragment;

/**
 * Created by Reist on 10/16/15.
 */
public class SandboxApplication extends BaseApplication {

    private final SandboxComponent sandboxComponent = DaggerSandboxComponent.builder()
            .sandboxModule(new SandboxModule())
            .baseModule(new BaseModule(this))
            .build();

    @Override
    public Object buildComponentFor(BaseView view) {

        Class<? extends BaseView> viewClass = view.getClass();
        Context context = view.getContext();

        if (RepoListFragment.class.isAssignableFrom(viewClass)) {
            return sandboxComponent.reposFragmentComponent(new BaseViewModule(context));
        } else {
            throw new RuntimeException("Unknown view class: " + viewClass.getName());
        }

    }

}
