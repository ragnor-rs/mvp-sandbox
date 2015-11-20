package io.reist.sandbox.repoedit.presenter;

import android.widget.Toast;

import javax.inject.Inject;

import io.reist.sandbox.R;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.ResponseObserver;
import io.reist.sandbox.core.presenter.BasePresenter;
import io.reist.sandbox.repoedit.view.RepoEditView;
import io.reist.sandbox.repolist.model.RepoService;
import rx.Subscriber;

/**
 * Created by defuera on 10/11/2015.
 */
public class RepoEditPresenter extends BasePresenter<RepoEditView> {

    public static final String EXTRA_REPO_ID = "io.reist.sandbox.extra_repo_id";

    RepoService repoService;

    private Repo repo;

    @Inject
    public RepoEditPresenter(RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    protected void onViewAttached() {
        long repoId = view().getExtras().getLong(EXTRA_REPO_ID);
        view().showLoader(true);
        subscribe(repoService.byId(repoId), new ResponseObserver<Repo>() {

            @Override
            protected void onFail(Response.Error error) {
                view().showLoader(false);
                view().displayError(error);
            }

            @Override
            protected void onSuccess(Repo data) {
                view().showLoader(false);
                repo = data;
                view().displayData(data);
            }
        });
    }

    public void saveRepo(String name, String author, String url) {
        repo.name = name;
        repo.owner.name = author;
        repo.url = url;

        subscribe(repoService.save(repo), new ResponseObserver<Repo>() {

            @Override
            protected void onFail(Response.Error error) {
                Toast.makeText(view().context(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onSuccess(Repo data) {
                Toast.makeText(view().context(), R.string.repo_saved, Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void deleteRepo() {
        subscribe(repoService.delete(repo.id), new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                view().back();
            }
        });
    }
}