package io.reist.sandbox.repos.mvp.view;

import android.app.ProgressDialog;
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
import io.reist.sandbox.repos.di.ReposFragmentComponent;
import io.reist.sandbox.repos.mvp.presenter.RepoListPresenter;

/**
 * Created by Reist on 10/13/15.
 */
public class RepoListFragment extends BaseFragment<RepoListPresenter> implements RepoListView {

    @Bind(R.id.daggertest_repo_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    RepoListPresenter presenter;

    private ProgressDialog loaderDialog;

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

        loaderDialog = new ProgressDialog(getActivity());
        loaderDialog.setMessage(getContext().getString(R.string.loading));

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
        if (show) {
            loaderDialog.show();
        } else {
            if (loaderDialog.isShowing()){
                loaderDialog.dismiss();
            }
        }
    }

}
