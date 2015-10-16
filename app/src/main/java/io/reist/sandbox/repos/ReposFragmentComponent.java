package io.reist.sandbox.repos;

import javax.inject.Singleton;

import dagger.Subcomponent;
import io.reist.sandbox.repos.view.RepoListFragment;

/**
 * Created by Reist on 10/16/15.
 */
@Singleton
@Subcomponent(modules = ReposFragmentModule.class)
public interface ReposFragmentComponent {

    void inject(RepoListFragment view);

}
