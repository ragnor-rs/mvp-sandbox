package io.reist.sandbox.repolist.di;

import dagger.Subcomponent;
import io.reist.sandbox.repolist.mvp.view.RepoListFragment;

@Subcomponent
public interface ReposFragmentComponent {

    void inject(RepoListFragment view);

}
