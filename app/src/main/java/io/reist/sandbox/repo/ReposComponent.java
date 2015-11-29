package io.reist.sandbox.repo;

import javax.inject.Singleton;

import dagger.Subcomponent;
import io.reist.sandbox.repo.view.RepoEditFragment;
import io.reist.sandbox.repo.view.RepoListFragment;

@Singleton
@Subcomponent(modules = ReposModule.class)
public interface ReposComponent {

    void inject(RepoListFragment view);

    void inject(RepoEditFragment view);

}
