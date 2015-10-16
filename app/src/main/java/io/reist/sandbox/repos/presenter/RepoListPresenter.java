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

    private static final String REPOS_AUTHOR = "JakeWharton";

    private final RepoService repoService;

    @Inject
    public RepoListPresenter(RepoService repoService) {
        this.repoService = repoService;
    }

    public void listRepos() {
        repoService.listRepos(REPOS_AUTHOR).enqueue(new AsyncResponse<List<Repo>>() {

            @Override
            public void onSuccess(List<Repo> result) {
                getView().getRecyclerView().setAdapter(new RepoListAdapter(result));
            }

            @Override
            public void onError(Throwable error) {
                Toast.makeText(getContext(), R.string.github_repo_list_error, Toast.LENGTH_LONG).show();
            }

        });
    }

}
