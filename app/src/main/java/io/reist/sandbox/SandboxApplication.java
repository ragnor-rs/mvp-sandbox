package io.reist.sandbox;

import android.content.Context;

import io.reist.sandbox.core.BaseApplication;
import io.reist.sandbox.core.BaseModule;
import io.reist.sandbox.core.BaseViewModule;
import io.reist.sandbox.core.view.BaseView;
import io.reist.sandbox.repos.view.RepoListFragment;

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
