package io.reist.sandbox.app;

import io.reist.sandbox.core.BaseApplication;
import io.reist.sandbox.core.BaseModule;
import io.reist.sandbox.core.view.BaseView;
import io.reist.sandbox.repoedit.view.RepoEditFragment;
import io.reist.sandbox.repolist.view.RepoListFragment;

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
        } else if (RepoEditFragment.class.isAssignableFrom(viewClass)) {
            return sandboxComponent.editReposComponent();
        } else {
            return null; //cur what shall I do
//            throw new RuntimeException("Unknown view class: " + viewClass.getName());
        }

    }

}
