package io.reist.sandbox.app;

import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.repo.ReposComponent;
import io.reist.sandbox.user.UsersComponent;

@Singleton
@Component(modules = SandboxModule.class)
public interface SandboxComponent {

    ReposComponent reposComponent();

    UsersComponent usersComponent();

}