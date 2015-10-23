package io.reist.sandbox.repos.mvp.model;

import java.util.ArrayList;
import java.util.List;

import io.reist.sandbox.core.mvp.model.ListObservable;

/**
 * Created by Reist on 10/16/15.
 */
public class DummyRepoListObservable extends ListObservable<Repo> {

    @Override
    public Integer put(List<Repo> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Repo> get() {
        List<Repo> reposList = new ArrayList<>();
        Repo testRepo = new Repo();
        testRepo.name = "test-repo";
        testRepo.url = "http://www.ya.ru/";
        reposList.add(testRepo);
        return reposList;
    }

}
