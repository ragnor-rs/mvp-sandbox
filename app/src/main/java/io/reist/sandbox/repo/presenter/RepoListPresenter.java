package io.reist.sandbox.repo.presenter;

import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reist.sandbox.R;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.ResponseObserver;
import io.reist.sandbox.repo.model.RepoService;
import io.reist.sandbox.repo.view.RepoListView;
import io.reist.visum.Error;
import io.reist.visum.presenter.BasePresenter;

@Singleton
public class RepoListPresenter extends BasePresenter<RepoListView> {

    private static final String TAG = RepoListPresenter.class.getName();

    private final RepoService repoService;
    private boolean mIsDataLoaded = false;

    @Inject
    public RepoListPresenter(RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    protected void onViewAttached() {
        mIsDataLoaded = false;
        view().showLoader(true);
        loadData();
    }

    public boolean isDataLoaded() {
        return mIsDataLoaded;
    }

    public void loadData() {
        subscribe(repoService.list(), new ResponseObserver<List<Repo>>() {

            @Override
            protected void onFail(io.reist.visum.Error error) {
                view().showLoader(false);
                view().displayError(error);
            }

            @Override
            protected void onSuccess(List<Repo> result) {
                mIsDataLoaded = true;
                view().showLoader(false);
                view().displayData(result); //cur need to check if view detached or crash can occure
            }
        });
    }

    public void createRepo() {
        view().showLoader(true);
        Random rand = new Random();
        Repo object = new Repo();

        object.id = (long) rand.nextInt(100);
        object.name = "name_" + object.id;
        object.url = "url";

        subscribe(repoService.save(object), new AddRepoSubscriber());
    }

    private class AddRepoSubscriber extends ResponseObserver<Repo> {
        @Override
        protected void onFail(Error error) {
            Log.e(TAG, "Error saving data" + error.getMessage());
            Toast.makeText(getContext(), R.string.github_repo_saving_list_error, Toast.LENGTH_LONG).show();
            view().showLoader(false);
        }

        @Override
        protected void onSuccess(Repo result) {
            Log.i(TAG, "success add repo subscriber");
            Toast.makeText(getContext(), R.string.repo_saved, Toast.LENGTH_LONG).show();
            view().showLoader(false);
        }

    }

}