package io.reist.sandbox.repos.model;

import java.util.ArrayList;
import java.util.List;

import io.reist.sandbox.core.model.BackgroundService;
import io.reist.sandbox.core.model.Func0;
import io.reist.sandbox.core.model.Observable;

/**
 * Created by Reist on 10/16/15.
 */
public class DummyRepoService extends BackgroundService implements RepoService {

    @Override
    public Observable<List<Repo>> reposList(String user) {
        return createObservable(new Func0<List<Repo>>() {

            @Override
            public List<Repo> call() {
                final List<Repo> reposList = new ArrayList<>();
                Repo testRepo = new Repo();
                testRepo.name = "test-repo";
                testRepo.url = "http://www.ya.ru/";
                reposList.add(testRepo);
                return reposList;
            }

        });
    }

    @Override
    public Observable<Integer> storeList(List<Repo> data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Boolean> store(Repo data) {
        throw new UnsupportedOperationException();
    }

}
