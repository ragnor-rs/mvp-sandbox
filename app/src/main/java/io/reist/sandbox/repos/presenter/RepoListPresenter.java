package io.reist.sandbox.repos.presenter;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.Bind;
import io.reist.sandbox.R;
import io.reist.sandbox.core.model.Observer;
import io.reist.sandbox.core.presenter.BasePresenter;
import io.reist.sandbox.repos.ReposFragmentComponent;
import io.reist.sandbox.repos.model.Repo;
import io.reist.sandbox.repos.model.RepoService;

@Singleton
public class RepoListPresenter extends BasePresenter {

    private static final String REPOS_AUTHOR = "JakeWharton";

    private final RepoService repoService;

    @Bind(R.id.daggertest_repo_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    public RepoListPresenter(RepoService repoService) {
        this.repoService = repoService;
    }

    public void listRepos() {
        repoService.reposList(REPOS_AUTHOR).subscribe(new Observer<List<Repo>>() {

            @Override
            public void onNext(List<Repo> t) {
                RepoListAdapter adapter = new RepoListAdapter(t);
                ((ReposFragmentComponent) getComponent()).inject(adapter);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(), R.string.github_repo_list_error, Toast.LENGTH_LONG).show();
            }

        });
    }

}
