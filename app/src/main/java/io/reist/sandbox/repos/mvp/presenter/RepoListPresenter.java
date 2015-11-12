package io.reist.sandbox.repos.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reist.sandbox.R;
import io.reist.sandbox.app.mvp.model.ResponseModel;
import io.reist.sandbox.app.mvp.model.ResponseModelObserver;
import io.reist.sandbox.core.mvp.presenter.BasePresenter;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoService;
import io.reist.sandbox.repos.mvp.view.RepoListView;
import rx.Subscriber;

@Singleton
public class RepoListPresenter extends BasePresenter<RepoListView> {

    private static final String TAG = RepoListPresenter.class.getName();

    private final RepoService repoService;

    @Inject
    public RepoListPresenter(RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    protected void onViewAttached() {
        view().showLoader(true);
        loadData();
    }

    public void loadData() {
        subscribe(repoService.list(), new ResponseModelObserver<List<Repo>>() {

            @Override
            protected void onFail(ResponseModel.Error error) {
                view().showLoader(false);
                view().displayError(error);
            }

            @Override
            protected void onSuccess(List<Repo> data) {
                view().showLoader(false);
                view().displayData(data); //cur need to check if view detached or crash can occure
            }
        });
    }

    public void createRepo() {
        view().showLoader(true);
        Random rand = new Random();
        Repo object = new Repo();

        object.id = Long.valueOf(rand.nextInt(100));
        object.author = "author";
        object.name = "name_" + object.id;
        object.url = "url";

        subscribe(repoService.save(object), new AddRepoSubscriber());
    }

    private class AddRepoSubscriber extends Subscriber<Boolean> {

        @Override
        public void onNext(Boolean success) {
            Log.i(TAG, "success add repo subscriber");
            Toast.makeText(getContext(), R.string.repo_saved, Toast.LENGTH_LONG).show();
            view().showLoader(false);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "Error saving data", e);
            Toast.makeText(getContext(), R.string.github_repo_saving_list_error, Toast.LENGTH_LONG).show();
            view().showLoader(false);
        }

        @Override
        public void onCompleted() {
        }

    }

}