package io.reist.sandbox.repolist;

import dagger.Subcomponent;
import io.reist.sandbox.repolist.view.RepoListFragment;

@Subcomponent
public interface ReposFragmentComponent {

    void inject(RepoListFragment view);

}
