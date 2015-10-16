package io.reist.sandbox.repos.model;

import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;

/**
 * Created by Reist on 10/14/15.
 */
public interface RepoService {

    AsyncRequest<List<Repo>> listRepos(String user);

}
