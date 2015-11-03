package io.reist.sandbox.repos.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import io.reist.sandbox.R;
import io.reist.sandbox.core.mvp.view.BaseFragment;
import io.reist.sandbox.core.mvp.view.BaseView;
import io.reist.sandbox.repos.di.ReposFragmentComponent;
import io.reist.sandbox.repos.mvp.presenter.RepoListPresenter;

/**
 * Created by Reist on 10/13/15.
 */
public class RepoListFragment extends BaseFragment<RepoListPresenter> implements BaseView {

    @Bind(R.id.daggertest_repo_recycler_view)
    RecyclerView mRecyclerView;

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
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

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

}
