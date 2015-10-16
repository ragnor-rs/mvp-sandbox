package io.reist.sandbox.repos.model.network;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.network.RetrofitService;
import io.reist.sandbox.repos.model.Repo;
import io.reist.sandbox.repos.model.RepoService;

/**
 * Created by Reist on 10/14/15.
 */
public class GitHubRepoService extends RetrofitService implements RepoService {

    private final GitHubApi gitHubApi;

    public GitHubRepoService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public AsyncRequest<List<Repo>> listRepos(final String user) {
        return createRequest(gitHubApi.listRepos(user));
    }

}
