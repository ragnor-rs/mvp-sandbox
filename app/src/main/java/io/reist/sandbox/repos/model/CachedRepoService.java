package io.reist.sandbox.repos.model;

import java.util.List;

import io.reist.sandbox.core.model.CachedService;
import io.reist.sandbox.core.model.Observable;
import io.reist.sandbox.core.model.Observer;

/**
 * Created by Reist on 10/17/15.
 */
public class CachedRepoService extends CachedService<Repo, RepoService> implements RepoService {

    public CachedRepoService(RepoService localRepoService, RepoService remoteRepoService) {
        super(localRepoService, remoteRepoService);
    }

    @Override
    public Observable<List<Repo>> reposList(final String user) {
        return new Observable<List<Repo>>() {

            @Override
            public void subscribe(final Observer<List<Repo>> observer) {
                enqueueReadListRequests(
                        localService.reposList(user),
                        remoteService.reposList(user),
                        observer
                );
            }

        };
    }

}
