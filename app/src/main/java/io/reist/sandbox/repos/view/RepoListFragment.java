package io.reist.sandbox.repos.view;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import io.reist.sandbox.R;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.view.widget.LoaderView;
import io.reist.sandbox.repos.ReposComponent;
import io.reist.sandbox.repos.presenter.RepoListAdapter;
import io.reist.sandbox.repos.presenter.RepoListPresenter;
import io.reist.visum.model.Error;
import io.reist.visum.view.BaseFragment;

/**
 * Created by Reist on 10/13/15.
 */
public class RepoListFragment extends BaseFragment<RepoListPresenter> implements RepoListView {

    @Bind(R.id.daggertest_repo_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.loader)
    LoaderView loaderView;

    @Inject
    RepoListPresenter presenter;

    private RepoListAdapter adapter;

    public RepoListFragment() {
        super(R.layout.github_fragment);
    }


    @Override
    protected void ready() {
        // setView this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // setView a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        loaderView.setOnRetryClickListener(v -> presenter.loadData());
    }

    @OnClick(R.id.create_repo_button)
    void onCreateRepoClicked() {
        presenter.createRepo();
    }

    @Override
    protected void inject(Object from) {
        ((ReposComponent) from).inject(this);
    }

    @Override
    public RepoListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showLoader(boolean show) {
        loaderView.showLoading(show);
    }

    @Override
    public void displayError(Error error) {
        if (adapter == null || adapter.getItemCount() == 0) {
            loaderView.showNetworkError();
        } else {
            Snackbar
                    .make(mRecyclerView, R.string.network_error, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, v -> presenter.loadData())
                    .show();
        }
    }

    @Override
    public void displayData(List<Repo> data) {
        loaderView.hide();
        adapter = new RepoListAdapter(data);
        adapter.setItemClickListener(repo -> {
            getFragmentController().showFragment(RepoEditFragment.newInstance(repo.id), false);
        });
        mRecyclerView.setAdapter(adapter);
    }

}
