package io.reist.sandbox.repos.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reist.sandbox.R;
import io.reist.sandbox.app.mvp.model.ResponseModel;
import io.reist.sandbox.core.mvp.presenter.BasePresenter;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoService;
import io.reist.sandbox.repos.mvp.view.RepoListView;
import rx.Observer;
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
        getView().showLoader(true);
        subscribe(repoService.list(), new RepoListObserver());
    }

    public void createRepo() {
        getView().showLoader(true);
        Random rand = new Random();
        Repo object = new Repo();

        object.id = Long.valueOf(rand.nextInt(100));
        object.author = "author";
        object.name = "name_" + object.id;
        object.url = "url";

        subscribe(repoService.save(object), new AddRepoSubscriber());
    }

    private class RepoListObserver implements Observer<ResponseModel<List<Repo>>> {

        @Override
        public void onNext(ResponseModel<List<Repo>> response) {
            Log.i(TAG, "--- OBSERVED ON " + Thread.currentThread() + " ---");
            if (response.isSuccesful()) {
                Log.d(TAG, "successfully loaded " + response.data.size() + " items");
                getView().displayData(response.data);
                getView().showLoader(false);
            } else {
                Log.w(TAG, "network error occured");
                getView().displayNetworkError(response.getError());
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "Error fetching data", e);
            Toast.makeText(getContext(), R.string.github_repo_loading_list_error, Toast.LENGTH_LONG).show();
            getView().showLoader(false);
        }

        @Override
        public void onCompleted() {
        }

    }

    private class AddRepoSubscriber extends Subscriber<Boolean> {

        @Override
        public void onNext(Boolean success) {
            Log.i(TAG, "success add repo subscriber");
            Toast.makeText(getContext(), R.string.github_repo_saved_successfully, Toast.LENGTH_LONG).show();
            getView().showLoader(false);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "Error saving data", e);
            Toast.makeText(getContext(), R.string.github_repo_saving_list_error, Toast.LENGTH_LONG).show();
            getView().showLoader(false);
        }

        @Override
        public void onCompleted() {
        }

    }

}
