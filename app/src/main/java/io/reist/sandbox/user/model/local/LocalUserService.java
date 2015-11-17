package io.reist.sandbox.user.model.local;

import java.util.List;

import io.reist.sandbox.app.model.User;
import io.reist.sandbox.user.model.UserService;

/**
 * Created by m039 on 11/17/15.
 */
public interface LocalUserService extends UserService {

    int saveSync(List<User> list);

}
