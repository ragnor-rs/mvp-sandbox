package io.reist.sandbox.repos.mvp.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.Bind;
import io.reist.sandbox.R;
import io.reist.sandbox.core.mvp.presenter.BasePresenter;
import io.reist.sandbox.core.mvp.view.BaseView;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoService;
import rx.Observer;
import rx.Subscriber;

@Singleton
public class RepoListPresenter extends BasePresenter {

    private static final String TAG = RepoListPresenter.class.getName();

    private final RepoService repoService;

    @Bind(R.id.daggertest_repo_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    public RepoListPresenter(RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    protected void onViewAttached(BaseView view) {
        subscribe(repoService.list(), new RepoListObserver());
    }

    @Override
    protected void onViewDetached() {
    }

    public void createRepo() {
//        getView().showProgress(); //todo parametrise Presenter to push ui updates
        Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
        Random rand = new Random();
        Repo object = new Repo();

        object.id = rand.nextLong();
        object.author = "author";
        object.name = "name_" + object.id;
        object.url = "url";

        subscribe(repoService.save(object), new AddRepoSubscriber());
    }

    private class RepoListObserver implements Observer<List<Repo>> {

        @Override
        public void onNext(List<Repo> repos) {
            mRecyclerView.setAdapter(new RepoListAdapter(repos));
            Log.i(TAG, "--- OBSERVED ON " + Thread.currentThread() + " ---");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "Error fetching data", e);
            Toast.makeText(getContext(), R.string.github_repo_loading_list_error, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCompleted() {
        }

    }

    private class AddRepoSubscriber extends Subscriber<Boolean> {
        @Override
        public void onNext(Boolean success) {

        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "Error saving data", e);
            Toast.makeText(getContext(), R.string.github_repo_saving_list_error, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCompleted() {
            Log.i(TAG, "success add repo subscriber");
            Toast.makeText(getContext(), R.string.github_repo_saved_successfully, Toast.LENGTH_LONG).show();
        }
    }
}
