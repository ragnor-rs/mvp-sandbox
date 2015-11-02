package io.reist.sandbox.repos.di;

import dagger.Subcomponent;
import io.reist.sandbox.repos.mvp.view.RepoListFragment;

@Subcomponent
public interface ReposFragmentComponent {

    void inject(RepoListFragment view);

}
