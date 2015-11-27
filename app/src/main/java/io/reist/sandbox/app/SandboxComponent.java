package io.reist.sandbox.app;

import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.repo.RepoEditComponent;
import io.reist.sandbox.repo.RepoModule;
import io.reist.sandbox.repo.ReposFragmentComponent;
import io.reist.sandbox.user.UserFragmentComponent;
import io.reist.sandbox.user.UserModule;

@Singleton
@Component(modules = SandboxModule.class)
public interface SandboxComponent {

    //region View scope components

    ReposFragmentComponent reposFragmentComponent();

    RepoEditComponent editReposComponent();

    UserFragmentComponent userFragmentComponent();

    //endregion

}
