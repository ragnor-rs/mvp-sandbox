package io.reist.sandbox.repolist.model;

import io.reist.sandbox.core.model.CachedService;

public class CachedRepoService extends CachedService<Repo> implements RepoService {

    public CachedRepoService(RepoService local, RepoService remote) {
        super(local, remote);
    }

}
