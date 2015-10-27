package io.reist.sandbox.repos.mvp.model;

import java.util.ArrayList;
import java.util.List;

import io.reist.sandbox.core.rx.Func0;
import io.reist.sandbox.core.rx.Observable;

/**
 * Created by Reist on 10/16/15.
 */
public class DummyRepoListObservable extends Observable<List<Repo>> {

    public DummyRepoListObservable() {
        super(null);
    }

    //@Override
    public Func0<List<Repo>> getEmittingFunction() {
        return new Func0<List<Repo>>() {

            @Override
            public List<Repo> call() {
                List<Repo> reposList = new ArrayList<>();
                Repo testRepo = new Repo();
                testRepo.name = "test-repo";
                testRepo.url = "http://www.ya.ru/";
                reposList.add(testRepo);
                return reposList;
            }
        };

    }

}
