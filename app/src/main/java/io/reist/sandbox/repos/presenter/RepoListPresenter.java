package io.reist.sandbox.repos.presenter;

import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import io.reist.sandbox.R;
import io.reist.sandbox.core.model.AsyncResponse;
import io.reist.sandbox.core.presenter.BasePresenter;
import io.reist.sandbox.repos.model.Repo;
import io.reist.sandbox.repos.model.RepoService;
import io.reist.sandbox.repos.view.RepoListView;

/**
 * Created by Reist on 10/13/15.
 */
public class RepoListPresenter extends BasePresenter<RepoListView> {

    private static final String GIT_HUB_USER = "JakeWharton";

    private final RepoService repoService;

    @Inject
    public RepoListPresenter(RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    public void update() {
        repoService.listRepos(GIT_HUB_USER).enqueue(new AsyncResponse<List<Repo>>() {

            @Override
            public void onSuccess(List<Repo> response) {
                getView().getRecyclerView().setAdapter(new RepoListAdapter(response));
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(getContext(), R.string.github_repo_list_error, Toast.LENGTH_LONG).show();
            }

        });
    }

}
