package io.reist.sandbox.app;

import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.repoedit.RepoEditComponent;
import io.reist.sandbox.repolist.ReposFragmentComponent;

@Singleton
@Component(modules = SandboxModule.class)
public interface SandboxComponent {

    //region View scope components

    ReposFragmentComponent reposFragmentComponent();

    RepoEditComponent editReposComponent();

    //endregion
}
