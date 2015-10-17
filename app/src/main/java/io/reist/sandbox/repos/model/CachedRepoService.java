package io.reist.sandbox.repos.model;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.AsyncResponse;
import io.reist.sandbox.core.model.CachedService;
import io.reist.sandbox.repos.model.local.LocalRepoService;

/**
 * Created by Reist on 10/17/15.
 */
public class CachedRepoService extends CachedService<Repo, LocalRepoService> implements RepoService {

    public final RepoService remoteRepoService;

    public CachedRepoService(LocalRepoService localRepoService, RepoService remoteRepoService) {
        super(localRepoService);
        this.remoteRepoService = remoteRepoService;
    }

    @Override
    public AsyncRequest<List<Repo>> listRepos(final String user) {
        return new AsyncRequest<List<Repo>>() {

            @Override
            public void enqueue(final AsyncResponse<List<Repo>> response) {
                enqueueLocalReadListRequest(
                        getLocalService().listRepos(user),
                        remoteRepoService.listRepos(user),
                        response
                );
            }

        };
    }

}
