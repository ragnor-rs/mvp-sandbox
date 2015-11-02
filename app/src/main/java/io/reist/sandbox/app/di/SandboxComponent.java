package io.reist.sandbox.app.di;

import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.repos.di.ReposFragmentComponent;

@Singleton
@Component(modules = SandboxModule.class)
public interface SandboxComponent {

    ReposFragmentComponent reposFragmentComponent();

}
