package io.reist.sandbox.user;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Provides;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.user.model.UserCachedService;
import io.reist.sandbox.user.model.UserService;
import io.reist.sandbox.user.model.local.StorIoUserService;
import io.reist.sandbox.user.model.remote.RetrofitUserService;


/**
 * Created by m039 on 11/12/15.
 */
@dagger.Module
public class UserModule {

    @Singleton
    @Provides
    UserService userService(GitHubApi gitHubApi, StorIOSQLite storIOSQLite) {
        return new UserCachedService(new StorIoUserService(storIOSQLite), new RetrofitUserService(gitHubApi));
    }

}
