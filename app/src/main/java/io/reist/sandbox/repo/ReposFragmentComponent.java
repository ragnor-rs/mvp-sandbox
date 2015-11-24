package io.reist.sandbox.repo;

import dagger.Subcomponent;
import io.reist.sandbox.repo.view.RepoListFragment;

@Subcomponent
public interface ReposFragmentComponent {

    void inject(RepoListFragment view);

}
