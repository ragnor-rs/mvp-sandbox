package io.reist.sandbox.app.di;

import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.repoedit.di.RepoEditComponent;
import io.reist.sandbox.repolist.di.ReposFragmentComponent;

@Singleton
@Component(modules = SandboxModule.class)
public interface SandboxComponent {

    //region View scope components

    ReposFragmentComponent reposFragmentComponent();

    RepoEditComponent editReposComponent();

    //endregion
}
