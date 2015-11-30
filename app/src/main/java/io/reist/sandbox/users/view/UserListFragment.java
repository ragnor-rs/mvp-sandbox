package io.reist.sandbox.users.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import io.reist.sandbox.R;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.app.view.widget.LoaderView;
import io.reist.sandbox.users.UsersComponent;
import io.reist.sandbox.users.presenter.UserListAdapter;
import io.reist.sandbox.users.presenter.UserListPresenter;
import io.reist.visum.view.BaseFragment;

/**
 * Created by m039 on 11/12/15.
 */
public class UserListFragment extends BaseFragment<UserListPresenter>
    implements UserListView
{

    public static UserListFragment newInstance() {
        return newInstance(UserListFragment.class, R.layout.fragment_users);
    }

    @Inject
    UserListPresenter mPresenter;

    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;

    @Bind(R.id.loader)
    LoaderView mLoaderView;

    UserListAdapter mAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter = new UserListAdapter());

        mAdapter.setOnUserClickListener(user -> getFragmentController().showFragment(UserReposFragment.newInstance(user.id), true));
    }

    @NonNull
    @Override
    public UserListPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(Object from) {
        ((UsersComponent) from).inject(this);
    }

    @Override
    public void displayData(List<User> users) {
        mAdapter.setUsers(users);
        mLoaderView.hide();
    }

    @Override
    public void displayError(io.reist.visum.model.Error error) {
        if (mAdapter == null || mAdapter.getItemCount() == 0) {
            mLoaderView.showNetworkError();
        } else {
            Snackbar
                    .make(mRecyclerView, R.string.network_error, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, v -> mPresenter.loadData())
                    .show();
        }
    }

    @Override
    public void showLoader(boolean show) {
        mLoaderView.showLoading(show);
    }
}
