package io.reist.sandbox;

import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.core.BaseViewModule;
import io.reist.sandbox.repos.ReposFragmentComponent;

@Singleton
@Component(modules = SandboxModule.class)
public interface SandboxComponent {

    ReposFragmentComponent reposFragmentComponent(BaseViewModule baseViewModule);

}
