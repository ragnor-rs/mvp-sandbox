package io.reist.sandbox.app;

import io.reist.sandbox.app.di.DaggerSandboxComponent;
import io.reist.sandbox.app.di.SandboxComponent;
import io.reist.sandbox.app.di.SandboxModule;
import io.reist.sandbox.core.BaseApplication;
import io.reist.sandbox.core.di.BaseModule;
import io.reist.sandbox.core.mvp.view.BaseView;
import io.reist.sandbox.editrepo.mvp.view.EditRepoFragment;
import io.reist.sandbox.repos.mvp.view.RepoListFragment;

/**
 * Created by Reist on 10/16/15.
 */
public class SandboxApplication extends BaseApplication {

    private final SandboxComponent sandboxComponent = DaggerSandboxComponent.builder()
            .sandboxModule(new SandboxModule())
            .baseModule(new BaseModule(this))
            .build();

    /**
     * Util method for creating new view component, every Component should be registered here
     */
    @Override
    public Object buildComponentFor(BaseView view) {

        Class<? extends BaseView> viewClass = view.getClass();

        if (RepoListFragment.class.isAssignableFrom(viewClass)) {
            return sandboxComponent.reposFragmentComponent();
        } else if (EditRepoFragment.class.isAssignableFrom(viewClass)) {
            return sandboxComponent.editReposComponent();
        } else {
            throw new RuntimeException("Unknown view class: " + viewClass.getName());
        }

    }

}
