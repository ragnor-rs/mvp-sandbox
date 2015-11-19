package io.reist.sandbox.repoedit.presenter;

import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Named;

import io.reist.sandbox.R;
import io.reist.sandbox.app.SandboxModule;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.ResponseObserver;
import io.reist.sandbox.core.presenter.BasePresenter;
import io.reist.sandbox.repoedit.view.RepoEditView;
import io.reist.sandbox.repolist.model.RepoService;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by defuera on 10/11/2015.
 */
public class RepoEditPresenter extends BasePresenter<RepoEditView> {

    public static final String EXTRA_REPO_ID = "io.reist.sandbox.extra_repo_id";

    RepoService repoService;
    private Repo repo;

    //todo since apiary not really working we cannot use remote service, since it provides incorrect data
    @Inject
    public RepoEditPresenter(@Named(SandboxModule.LOCAL_SERVICE) RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    protected void onViewAttached() {
        long repoId = view().getArguments().getLong(EXTRA_REPO_ID);
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

        subscribe(repoService.save(repo), new Observer<Boolean>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(view().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Boolean success) {
                if (success)
                    Toast.makeText(view().getContext(), R.string.repo_saved, Toast.LENGTH_SHORT).show();
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