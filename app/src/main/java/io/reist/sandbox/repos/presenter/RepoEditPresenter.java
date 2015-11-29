package io.reist.sandbox.repos.presenter;

import android.widget.Toast;

import javax.inject.Inject;

import io.reist.sandbox.R;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.ResponseObserver;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.sandbox.repos.view.RepoEditView;
import io.reist.visum.Error;
import io.reist.visum.model.Response;
import io.reist.visum.presenter.BasePresenter;
import rx.Subscriber;

/**
 * Created by defuera on 10/11/2015.
 */
public class RepoEditPresenter extends BasePresenter<RepoEditView> {

    public static final String EXTRA_REPO_ID = "io.reist.sandbox.extra_repo_id";

    private RepoService repoService;

    private Repo repo;

    @Inject
    public RepoEditPresenter(RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    protected void onViewAttached() {
        long repoId = view().extras().getLong(EXTRA_REPO_ID);
        view().showLoader(true);
        subscribe(repoService.byId(repoId), new ResponseObserver<Repo>() {

            @Override
            protected void onFail(Error error) {
                view().showLoader(false);
                view().displayError(error);
            }

            @Override
            protected void onSuccess(Repo result) {
                view().showLoader(false);
                repo = result;
                view().displayData(result);
            }

        });
    }

    public void saveRepo(String name, String author, String url) {
        repo.name = name;
        repo.owner.name = author;
        repo.url = url;

        subscribe(repoService.save(repo), new ResponseObserver<Repo>() {

            @Override
            protected void onFail(Error error) {
                Toast.makeText(view().context(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onSuccess(Repo result) {
                Toast.makeText(view().context(), R.string.repo_saved, Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void deleteRepo() {
        subscribe(repoService.delete(repo.id), new Subscriber<Response<Integer>>() {

            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Response<Integer> response) {
                view().back();
            }

        });
    }

}