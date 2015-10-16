package io.reist.sandbox;

import android.content.Context;

import io.reist.sandbox.core.BaseApplication;
import io.reist.sandbox.core.view.BaseView;
import io.reist.sandbox.repos.ReposFragmentModule;
import io.reist.sandbox.repos.view.RepoListView;

/**
 * Created by Reist on 10/16/15.
 */
public class SandboxApplication extends BaseApplication {

    private final ApplicationComponent applicationComponent = DaggerApplicationComponent.create();

    @Override
    public Object buildComponentFor(BaseView view) {

        Class<? extends BaseView> viewClass = view.getClass();
        Context context = view.getContext();

        if (RepoListView.class.isAssignableFrom(viewClass)) {
            return applicationComponent.reposComponent(new ReposFragmentModule(context));
        } else {
            throw new RuntimeException("Unknown view class: " + viewClass.getName());
        }

    }

}
