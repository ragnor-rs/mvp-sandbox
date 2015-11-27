package io.reist.sandbox.user.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import io.reist.sandbox.R;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.view.widget.LoaderView;
import io.reist.visum.view.BaseFragment;
import io.reist.sandbox.user.UserFragmentComponent;
import io.reist.sandbox.user.presenter.UserReposPresenter;

/**
 * Created by Reist on 10/13/15.
 */
public class UserReposFragment extends BaseFragment<UserReposPresenter> implements UserReposView {

    private static final String ARG_USER = "arg_user";

    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;

    @Bind(R.id.loader)
    LoaderView loaderView;

    @Inject
    UserReposPresenter presenter;

    private UserReposAdapter adapter;
    private Long mUserId;

    public static UserReposFragment newInstance(Long userId) {
        UserReposFragment f = newInstance(UserReposFragment.class, R.layout.fragment_user_repos);

        f.getArguments().putLong(ARG_USER, userId);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserId = getArguments().getLong(ARG_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // setView this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // setView a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter = new UserReposAdapter());

        adapter.setOnLikeRepoClickListener(new UserReposAdapter.OnLikeRepoClickListener() {

            @Override
            public void onLikeRepoClick(Repo repo) {
                presenter.like(repo);
            }

            @Override
            public void onUnlikeRepoClick(Repo repo) {
                presenter.unlike(repo);
            }

        });

        loaderView.setOnRetryClickListener(v -> presenter.loadData());
        return view;
    }

    @Override
    protected void inject(Object from) {
        ((UserFragmentComponent) from).inject(this);
    }

    @NonNull
    @Override
    public UserReposPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showLoader(boolean show) {
        loaderView.showLoading(show);
    }

    @Override
    public Long getUserId() {
        return mUserId;
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
        adapter.setRepos(data);
    }

}
