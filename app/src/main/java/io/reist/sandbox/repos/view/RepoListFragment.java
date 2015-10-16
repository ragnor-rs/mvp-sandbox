package io.reist.sandbox.repos.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.reist.sandbox.R;
import io.reist.sandbox.core.view.BaseFragment;
import io.reist.sandbox.repos.ReposFragmentComponent;
import io.reist.sandbox.repos.presenter.RepoListPresenter;

/**
 * Created by Reist on 10/13/15.
 */
public class RepoListFragment extends BaseFragment<RepoListPresenter> implements RepoListView {

    private RecyclerView mRecyclerView;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    @Inject
    RepoListPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.github_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.daggertest_repo_recycler_view);

        // setView this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // setView a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.listRepos();
    }

    @Override
    public void onDestroyView() {
        mRecyclerView = null;
        mLayoutManager = null;
        super.onDestroyView();
    }

    @Override
    protected void inject(Object component) {
        ((ReposFragmentComponent) component).inject(this);
    }

    @Override
    protected void onPresenterAttached() {
        presenter.setView(this);
    }

    @Override
    protected void onPresenterDetached() {
        presenter.setView(null);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

}
