package io.reist.sandbox;

import javax.inject.Singleton;

import dagger.Component;
import io.reist.sandbox.core.BaseModule;
import io.reist.sandbox.repos.ReposFragmentComponent;

/**
 * Created by Reist on 10/14/15.
 */
@Singleton
@Component(modules = SandboxModule.class)
public interface SandboxComponent {

    ReposFragmentComponent reposComponent(BaseModule baseModule);

}
