package io.reist.sandbox.app;

import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.repoedit.RepoEditComponent;
import io.reist.sandbox.repolist.ReposFragmentComponent;
import io.reist.sandbox.user.UserFragmentComponent;

@Singleton
@Component(modules = SandboxModule.class)
public interface SandboxComponent {

    //region View scope components

    ReposFragmentComponent reposFragmentComponent();

    RepoEditComponent editReposComponent();

    UserFragmentComponent userFragmentComponent();

    //endregion
}
