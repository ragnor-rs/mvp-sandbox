package io.reist.sandbox.repo;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.app.SandboxModule;
import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.repo.model.CachedRepoService;
import io.reist.sandbox.repo.model.RepoService;
import io.reist.sandbox.repo.model.local.StorIoRepoService;
import io.reist.sandbox.repo.model.remote.RetrofitRepoService;

/**
 * Created by m039 on 11/27/15.
 */
@Module
public class RepoModule {

    @Provides
    @Singleton
    @Named(SandboxModule.LOCAL_SERVICE)
    protected RepoService localRepoService(StorIOSQLite storIoSqLite) {
        return new StorIoRepoService(storIoSqLite);
    }

    @Provides @Singleton @Named(SandboxModule.REMOTE_SERVICE)
    protected RepoService remoteRepoService(GitHubApi gitHubApi) {
        return new RetrofitRepoService(gitHubApi);
    }

    @Provides @Singleton
    protected RepoService repoService(
            @Named(SandboxModule.LOCAL_SERVICE) RepoService local,
            @Named(SandboxModule.REMOTE_SERVICE) RepoService remote
    ) {
        return new CachedRepoService(local, remote);
    }

}
