package io.reist.sandbox.repos.di;

import dagger.Subcomponent;
import io.reist.sandbox.core.di.BaseViewModule;
import io.reist.sandbox.repos.mvp.presenter.RepoListAdapter;
import io.reist.sandbox.repos.mvp.view.RepoListFragment;

@Subcomponent(modules = BaseViewModule.class)
public interface ReposFragmentComponent {

    void inject(RepoListFragment view);

    void inject(RepoListAdapter adapter);

}
