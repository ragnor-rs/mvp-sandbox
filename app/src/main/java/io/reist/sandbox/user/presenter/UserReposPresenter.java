package io.reist.sandbox.user.presenter;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reist.sandbox.R;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.core.presenter.BasePresenter;
import io.reist.sandbox.user.model.UserReposService;
import io.reist.sandbox.user.view.UserReposView;
import rx.Observer;

@Singleton
public class UserReposPresenter extends BasePresenter<UserReposView> {

    private static final String TAG = UserReposPresenter.class.getName();

    private final UserReposService repoService;

    @Inject
    public UserReposPresenter(UserReposService repoService) {
        this.repoService = repoService;
    }

    @Override
    protected void onViewAttached() {
        view().showLoader(true);
        loadData();
    }

    public void like(Repo repo) {
        repoService.like(view().getUser(), repo);
    }

    public void unlike(Repo repo) {
        repoService.unlike(view().getUser(), repo);
    }

    public void loadData() {
        subscribe(repoService.findReposByUser(view().getUser()), new RepoListObserver());
    }

    private class RepoListObserver implements Observer<Response<List<Repo>>> {

        @Override
        public void onNext(Response<List<Repo>> response) {
            Log.i(TAG, "--- OBSERVED ON " + Thread.currentThread() + " ---");
            UserReposView view = view();
            if (response.isSuccessful()) {
                Log.d(TAG, "successfully loaded " + response.getData().size() + " items");
                view.displayData(response.getData());
                view.showLoader(false);
            } else {
                Log.w(TAG, "network error occured");
                view.displayError(response.getError());
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "Error fetching data", e);
            Toast.makeText(getContext(), R.string.github_repo_loading_list_error, Toast.LENGTH_LONG).show();
            view().showLoader(false);
        }

        @Override
        public void onCompleted() {
        }

    }

}
