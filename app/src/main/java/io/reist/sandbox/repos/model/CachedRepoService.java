package io.reist.sandbox.repos.model;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.AsyncResponse;
import io.reist.sandbox.core.model.CachedService;

/**
 * Created by Reist on 10/17/15.
 */
public class CachedRepoService extends CachedService<Repo, RepoService> implements RepoService {

    public CachedRepoService(RepoService localRepoService, RepoService remoteRepoService) {
        super(localRepoService, remoteRepoService);
    }

    @Override
    public AsyncRequest<List<Repo>> listRepos(final String user) {
        return new AsyncRequest<List<Repo>>() {

            @Override
            public void enqueue(final AsyncResponse<List<Repo>> response) {
                enqueueReadListRequests(
                        localService.listRepos(user),
                        remoteService.listRepos(user),
                        response
                );
            }

        };
    }

}
