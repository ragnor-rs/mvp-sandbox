package io.reist.sandbox.repos;

import javax.inject.Singleton;

import dagger.Subcomponent;
import io.reist.sandbox.core.BaseViewModule;
import io.reist.sandbox.repos.presenter.RepoListAdapter;
import io.reist.sandbox.repos.view.RepoListFragment;

/**
 * Created by Reist on 10/16/15.
 */
@Singleton
@Subcomponent(modules = BaseViewModule.class)
public interface ReposFragmentComponent {

    void inject(RepoListFragment view);

    void inject(RepoListAdapter adapter);

}
