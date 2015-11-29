package io.reist.sandbox.app;

import android.content.Context;

import io.reist.sandbox.repos.view.RepoEditFragment;
import io.reist.sandbox.repos.view.RepoListFragment;
import io.reist.sandbox.users.view.UserListFragment;
import io.reist.sandbox.users.view.UserReposFragment;
import io.reist.visum.BaseModule;
import io.reist.visum.ComponentCache;
import io.reist.visum.view.BaseView;

/**
 * Created by Reist on 29.11.15.
 */
public class SandboxComponentCache extends ComponentCache {

    private final SandboxComponent sandboxComponent;

    public SandboxComponentCache(Context context) {
        this.sandboxComponent  = DaggerSandboxComponent.builder()
                .sandboxModule(new SandboxModule())
                .baseModule(new BaseModule(context))
                .build();
    }

    @Override
    public Object buildComponentFor(Class<? extends BaseView> viewClass) {
        if (
                RepoListFragment.class.isAssignableFrom(viewClass) ||
                RepoEditFragment.class.isAssignableFrom(viewClass)
        ) {
            return sandboxComponent.reposComponent();
        } else if (
                UserListFragment.class.isAssignableFrom(viewClass) ||
                UserReposFragment.class.isAssignableFrom(viewClass)
        ) {
            return sandboxComponent.usersComponent();
        } else {
            throw new RuntimeException("Unknown view class: " + viewClass.getName());
        }
    }

}
