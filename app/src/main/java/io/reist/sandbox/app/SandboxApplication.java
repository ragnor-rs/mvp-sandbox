package io.reist.sandbox.app;

import android.util.Log;

import io.reist.sandbox.core.BaseApplication;
import io.reist.sandbox.core.BaseModule;
import io.reist.sandbox.core.view.BaseView;
import io.reist.sandbox.repoedit.view.RepoEditFragment;
import io.reist.sandbox.repolist.view.RepoListFragment;

/**
 * Created by Reist on 10/16/15.
 */
public class SandboxApplication extends BaseApplication {

    private static final String TAG = SandboxApplication.class.getSimpleName();

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
            Log.w(TAG, "Unknown view class: \" + viewClass.getName()");
            return null;
        }

    }

}
