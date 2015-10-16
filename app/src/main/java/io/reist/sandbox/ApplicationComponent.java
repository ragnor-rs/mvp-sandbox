package io.reist.sandbox;

import dagger.Component;
import io.reist.sandbox.repos.ReposFragmentComponent;
import io.reist.sandbox.repos.ReposFragmentModule;

/**
 * Created by Reist on 10/14/15.
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    ReposFragmentComponent reposComponent(ReposFragmentModule reposFragmentModule);

}
