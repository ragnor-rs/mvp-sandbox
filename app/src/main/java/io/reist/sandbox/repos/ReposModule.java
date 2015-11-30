package io.reist.sandbox.repos;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.app.SandboxModule;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.repos.model.CachedRepoService;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.sandbox.repos.model.local.StorIoRepoService;
import io.reist.sandbox.repos.model.remote.RetrofitRepoService;

/**
 * Created by Reist on 29.11.15.
 */
@Module
public class ReposModule {

    @Provides @Singleton @Named(SandboxModule.LOCAL_SERVICE)
    RepoService localRepoService(StorIOSQLite storIoSqLite) {
        return new StorIoRepoService(storIoSqLite);
    }

    @Provides @Singleton @Named(SandboxModule.REMOTE_SERVICE)
    RepoService remoteRepoService(GitHubApi gitHubApi) {
        return new RetrofitRepoService(gitHubApi);
    }

    @Provides @Singleton
    RepoService repoService(
            @Named(SandboxModule.LOCAL_SERVICE) RepoService local,
            @Named(SandboxModule.REMOTE_SERVICE) RepoService remote
    ) {
        return new CachedRepoService(local, remote);
    }

}
