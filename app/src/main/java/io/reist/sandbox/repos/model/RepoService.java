package io.reist.sandbox.repos.model;

import java.util.List;

import io.reist.sandbox.core.model.EntityService;
import io.reist.sandbox.core.model.Observable;

/**
 * Created by Reist on 10/14/15.
 */
public interface RepoService extends EntityService<Repo> {

    Observable<List<Repo>> reposList(String user);

}
