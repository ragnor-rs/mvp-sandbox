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
import io.reist.sandbox.repos.di.ReposFragmentComponent;
import io.reist.sandbox.repos.mvp.model.Repo;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class RepoListPresenter extends BasePresenter {

    private static final String TAG = RepoListPresenter.class.getName();

    private final Observable<List<Repo>> repoListObservable;

    @Bind(R.id.daggertest_repo_recycler_view)
    RecyclerView mRecyclerView;

    private Subscription repoListSubscription;

    @Inject
    public RepoListPresenter(Observable<List<Repo>> repoListObservable) {
        this.repoListObservable = repoListObservable;
    }

    @Override
    protected void onViewAttached(BaseView view) {
        repoListSubscription = repoListObservable
                .last()
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
            Log.e(TAG, "--- " + Thread.currentThread() + " OBSERVED ---");
            RepoListAdapter adapter = new RepoListAdapter(repos);
            ((ReposFragmentComponent) getComponent()).inject(adapter);
            mRecyclerView.setAdapter(adapter);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "Observed error", e);
            Toast.makeText(getContext(), R.string.github_repo_list_error, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCompleted() {}

    }

}
