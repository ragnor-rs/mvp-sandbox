package io.reist.sandbox.repos.model.github;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.AsyncResponse;
import io.reist.sandbox.repos.model.Repo;
import io.reist.sandbox.repos.model.RepoService;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Reist on 10/14/15.
 */
public class GitHubRepoService implements RepoService {

    private final GitHubApi gitHubApi;

    public GitHubRepoService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public AsyncRequest<List<Repo>> listRepos(final String user) {
        return new AsyncRequest<List<Repo>>() {

            @Override
            public void enqueue(final AsyncResponse<List<Repo>> asyncResponse) {
                gitHubApi.listRepos(user).enqueue(new Callback<List<Repo>>() {

                    @Override
                    public void onResponse(Response<List<Repo>> response, Retrofit retrofit) {
                        asyncResponse.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        asyncResponse.onError(t);
                    }

                });
            }

        };
    }

}
