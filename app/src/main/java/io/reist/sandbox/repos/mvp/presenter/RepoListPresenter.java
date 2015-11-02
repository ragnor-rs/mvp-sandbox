package io.reist.sandbox.repos.mvp.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.Bind;
import io.reist.sandbox.R;
import io.reist.sandbox.core.mvp.presenter.BasePresenter;
import io.reist.sandbox.core.mvp.view.BaseView;
import io.reist.sandbox.core.rx.AndroidSchedulers;
import io.reist.sandbox.core.rx.Observer;
import io.reist.sandbox.core.rx.Schedulers;
import io.reist.sandbox.core.rx.Subscription;
import io.reist.sandbox.repos.di.ReposFragmentComponent;
import io.reist.sandbox.repos.mvp.model.Repo;
import io.reist.sandbox.repos.mvp.model.RepoService;

@Singleton
public class RepoListPresenter extends BasePresenter {

    private static final String TAG = RepoListPresenter.class.getName();

    private final RepoService repoService;

    @Bind(R.id.daggertest_repo_recycler_view)
    RecyclerView mRecyclerView;

    private Subscription repoListSubscription;

    @Inject
    public RepoListPresenter(RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    protected void onViewAttached(BaseView view) {
        repoListSubscription = repoService.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RepoListObserver());
    }

    @Override
    protected void onViewDetached() {
        repoListSubscription.unsubscribe();
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
            Toast.makeText(getContext(), R.string.github_repo_list_error, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCompleted() {}

    }

}
