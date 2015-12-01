package io.reist.sandbox.users;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.repos.ReposModule;
import io.reist.sandbox.users.model.CachedUserService;
import io.reist.sandbox.users.model.UserService;
import io.reist.sandbox.users.model.local.StorIoUserService;
import io.reist.sandbox.users.model.remote.RetrofitUserService;

/**
 * Created by m039 on 11/12/15.
 */
@Module
public class UsersModule {

    @Singleton
    @Provides
    protected UserService userService(GitHubApi gitHubApi, StorIOSQLite storIOSQLite) {
        return new CachedUserService(new StorIoUserService(storIOSQLite), new RetrofitUserService(gitHubApi));
    }

}
