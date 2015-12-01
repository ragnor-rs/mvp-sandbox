package io.reist.sandbox.users.model;

import io.reist.sandbox.app.model.User;
import io.reist.visum.model.CachedService;

/**
 * Created by m039 on 11/12/15.
 */
public class CachedUserService
        extends CachedService<User>
        implements UserService
{

    public CachedUserService(UserService local, UserService remote) {
        super(local, remote);
    }

}
