package io.reist.sandbox.repolist.mvp.model;

import io.reist.sandbox.core.mvp.model.CachedService;

public class CachedRepoService extends CachedService<Repo> implements RepoService {

    public CachedRepoService(RepoService local, RepoService remote) {
        super(local, remote);
    }

}
