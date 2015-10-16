package io.reist.sandbox.repos.model;

import java.util.ArrayList;
import java.util.List;

import io.reist.sandbox.core.model.AsyncRequest;
import io.reist.sandbox.core.model.BackgroundOp;
import io.reist.sandbox.core.model.BackgroundService;

/**
 * Created by Reist on 10/16/15.
 */
public class DummyRepoService extends BackgroundService implements RepoService {

    @Override
    public AsyncRequest<List<Repo>> listRepos(String user) {
        return createRequest(new BackgroundOp<List<Repo>>() {

            @Override
            public List<Repo> execute() {
                final List<Repo> reposList = new ArrayList<>();
                Repo testRepo = new Repo();
                testRepo.name = "test-repo";
                testRepo.url = "http://www.ya.ru/";
                reposList.add(testRepo);
                return reposList;
            }

        });
    }

}
