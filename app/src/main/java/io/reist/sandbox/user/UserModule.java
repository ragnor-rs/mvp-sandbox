package io.reist.sandbox.user;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Provides;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.user.model.UserModelService;
import io.reist.sandbox.user.model.UserReposModelService;
import io.reist.sandbox.user.model.UserReposService;
import io.reist.sandbox.user.model.UserService;
import io.reist.sandbox.user.model.local.StorIoUserReposService;
import io.reist.sandbox.user.model.local.StorIoUserService;
import io.reist.sandbox.user.model.remote.RetrofitUserReposService;
import io.reist.sandbox.user.model.remote.RetrofitUserService;

/**
 * Created by m039 on 11/12/15.
 */
@dagger.Module
public class UserModule {

    @Singleton
    @Provides
    UserService userService(GitHubApi gitHubApi, StorIOSQLite storIOSQLite) {
        return new UserModelService(new StorIoUserService(storIOSQLite), new RetrofitUserService(gitHubApi));
    }

    @Singleton
    @Provides
    UserReposService userReposService(GitHubApi gitHubApi, StorIOSQLite storIOSQLite) {
        return new UserReposModelService(new StorIoUserReposService(storIOSQLite), new RetrofitUserReposService(gitHubApi));
    }

}
