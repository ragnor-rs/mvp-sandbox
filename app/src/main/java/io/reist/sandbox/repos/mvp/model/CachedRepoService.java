package io.reist.sandbox.repos.mvp.model;

import io.reist.sandbox.core.mvp.model.BaseService;
import io.reist.sandbox.core.mvp.model.CachedService;

public class CachedRepoService extends CachedService<Repo> {

    public CachedRepoService(BaseService<Repo> local, BaseService<Repo> remote) {
        super(local, remote);
    }

}
