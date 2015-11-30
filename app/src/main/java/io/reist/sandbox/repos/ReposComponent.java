package io.reist.sandbox.repos;

import javax.inject.Singleton;

import dagger.Subcomponent;
import io.reist.sandbox.repos.view.RepoEditFragment;
import io.reist.sandbox.repos.view.RepoListFragment;

@Singleton
@Subcomponent
public interface ReposComponent {

    void inject(RepoListFragment view);

    void inject(RepoEditFragment view);

}
