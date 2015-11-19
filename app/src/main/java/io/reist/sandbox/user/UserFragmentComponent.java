package io.reist.sandbox.user;

import javax.inject.Singleton;

import dagger.Subcomponent;
import io.reist.sandbox.user.view.UserReposFragment;
import io.reist.sandbox.user.view.UsersFragment;

/**
 * Created by m039 on 11/12/15.
 */
@Singleton
@Subcomponent(modules = UserModule.class)
public interface UserFragmentComponent {

    void inject(UsersFragment userFragment);

    void inject(UserReposFragment userReposFragment);

}
