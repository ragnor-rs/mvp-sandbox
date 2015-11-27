package io.reist.sandbox.repo;

import javax.inject.Singleton;

import dagger.Subcomponent;
import io.reist.sandbox.repo.view.RepoListFragment;

@Singleton
@Subcomponent
public interface ReposFragmentComponent {

    void inject(RepoListFragment view);

}
