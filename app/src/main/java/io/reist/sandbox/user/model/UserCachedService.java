package io.reist.sandbox.user.model;

import io.reist.sandbox.app.model.User;
import io.reist.visum.model.CachedService;

/**
 * Created by m039 on 11/12/15.
 */
public class UserCachedService
        extends CachedService<User>
        implements UserService
{

    public UserCachedService(UserService local, UserService remote) {
        super(local, remote);
    }

}
