package io.reist.sandbox;

import dagger.Module;
import dagger.Provides;
import io.reist.sandbox.repos.model.DummyRepoService;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.sandbox.repos.model.network.GitHubApi;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Reist on 10/16/15.
 */
@Module
public class ApplicationModule {

    private static final String GIT_HIB_BASE_URL = "https://api.github.com";

    @Provides
    RepoService repoService() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GIT_HIB_BASE_URL)
                .build();

        GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

        //return new GitHubRepoService(gitHubApi);
        return new DummyRepoService();

    }

}
