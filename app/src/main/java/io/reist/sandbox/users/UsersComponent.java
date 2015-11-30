package io.reist.sandbox.users;

import javax.inject.Singleton;

import dagger.Subcomponent;
import io.reist.sandbox.users.view.UserListFragment;

/**
 * Created by m039 on 11/12/15.
 */
@Singleton
@Subcomponent
public interface UsersComponent {

    void inject(UserListFragment userFragment);

    void inject(io.reist.sandbox.users.view.UserReposFragment userReposFragment);

}
