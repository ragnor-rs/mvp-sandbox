package io.reist.sandbox.repolist.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import io.reist.sandbox.R;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.view.widget.LoaderView;
import io.reist.visum.view.BaseFragment;
import io.reist.sandbox.repoedit.presenter.RepoEditPresenter;
import io.reist.sandbox.repoedit.view.RepoEditFragment;
import io.reist.sandbox.repolist.ReposFragmentComponent;
import io.reist.sandbox.repolist.presenter.RepoListAdapter;
import io.reist.sandbox.repolist.presenter.RepoListPresenter;

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

    public static final RepoListFragment newInstance() {
        return newInstance(RepoListFragment.class, R.layout.github_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // setView this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // setView a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        loaderView.setOnRetryClickListener(v -> presenter.loadData());
        return view;
    }

    @OnClick(R.id.create_repo_button)
    void onCreateRepoClicked() {
        presenter.createRepo();
    }

    @Override
    protected void inject(Object from) {
        ((ReposFragmentComponent) from).inject(this);
    }

    @Override
    protected RepoListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showLoader(boolean show) {
        loaderView.showLoading(show);
    }

    @Override
    public void displayError(io.reist.visum.Error error) {
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
            RepoEditFragment fragment = RepoEditFragment.newInstance();
            fragment.getArguments().putLong(RepoEditPresenter.EXTRA_REPO_ID, repo.id);
            getFragmentController().showFragment(fragment, false);
        });
        mRecyclerView.setAdapter(adapter);
    }

}
