package io.reist.sandbox.core.model.remote;

import io.reist.sandbox.app.model.remote.GitHubApi;
import io.reist.sandbox.core.model.AbstractBaseService;

/**
 * Created by Reist on 11/2/15.
 */
public abstract class RetrofitService<T> extends AbstractBaseService<T> {

    protected final GitHubApi gitHubApi;

    public RetrofitService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

}
