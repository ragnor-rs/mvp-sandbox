package io.reist.sandbox.app;

import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.repos.ReposComponent;
import io.reist.sandbox.users.UsersComponent;

@Singleton
@Component(modules = SandboxModule.class)
public interface SandboxComponent {

    ReposComponent reposComponent();

    UsersComponent usersComponent();

}