package io.reist.sandbox.user;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.repo.ReposModule;
import io.reist.sandbox.user.model.CachedUserService;
import io.reist.sandbox.user.model.UserService;
import io.reist.sandbox.user.model.local.StorIoUserService;
import io.reist.sandbox.user.model.remote.RetrofitUserService;

/**
 * Created by m039 on 11/12/15.
 */
@Module(includes = ReposModule.class)
public class UsersModule {

    @Singleton
    @Provides
    UserService userService(GitHubApi gitHubApi, StorIOSQLite storIOSQLite) {
        return new CachedUserService(new StorIoUserService(storIOSQLite), new RetrofitUserService(gitHubApi));
    }

}
