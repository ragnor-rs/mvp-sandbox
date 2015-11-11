package io.reist.sandbox.editrepo.mvp.presenter;

import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Named;

import io.reist.sandbox.R;
import io.reist.sandbox.app.di.SandboxModule;
import io.reist.sandbox.app.mvp.model.ResponseModel;
import io.reist.sandbox.core.mvp.presenter.BasePresenter;
import io.reist.sandbox.editrepo.mvp.view.EditRepoView;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoService;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by defuera on 10/11/2015.
 */
public class EditRepoPresenter extends BasePresenter<EditRepoView> {

    public static final String EXTRA_REPO_ID = "io.reist.sandbox.extra_repo_id";

    RepoService repoService;
    private Repo repo;

    @Inject
    public EditRepoPresenter(@Named(SandboxModule.LOCAL_SERVICE) RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    protected void onViewAttached() {
        long repoId = getView().getArguments().getLong(EXTRA_REPO_ID);
        getView().showLoader(true);
        subscribe(repoService.byId(repoId), new Subscriber<ResponseModel<Repo>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ResponseModel.Error error = new ResponseModel.Error(getContext().getString(R.string.error_unexpected));
                getView().displayError(error);
                getView().showLoader(false);
            }

            @Override
            public void onNext(ResponseModel<Repo> response) {
                getView().showLoader(false);
                if (!response.isSuccessful()) { //cur move to Subscriber wrapper?
                    getView().displayError(response.getError());
                } else {
                    repo = response.getData();
                    getView().displayData(response.getData());
                }
            }
        });
    }

    public void saveRepo(String name, String author, String url) {
        repo.name = name;
        repo.author = author;
        repo.url = url;


        subscribe(repoService.save(repo), new Observer<Boolean>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getView().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Boolean success) {
                if (success)
                    Toast.makeText(getView().getContext(), R.string.repo_saved, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteRepo() {
//        subscribe(repoService.delete(repo));
    }
}
