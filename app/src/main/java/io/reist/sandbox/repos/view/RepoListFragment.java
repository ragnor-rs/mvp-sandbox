package io.reist.sandbox.repos.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import io.reist.sandbox.R;
import io.reist.sandbox.core.view.BaseFragment;
import io.reist.sandbox.core.view.BaseView;
import io.reist.sandbox.repos.ReposFragmentComponent;
import io.reist.sandbox.repos.presenter.RepoListPresenter;

/**
 * Created by Reist on 10/13/15.
 */
public class RepoListFragment extends BaseFragment<RepoListPresenter> implements BaseView {

    @Bind(R.id.daggertest_repo_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    RepoListPresenter presenter;

    public RepoListFragment() {
        super(R.layout.github_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

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
    protected void inject(Object from) {
        ((ReposFragmentComponent) from).inject(this);
    }

    @Override
    protected RepoListPresenter getPresenter() {
        return presenter;
    }

}
