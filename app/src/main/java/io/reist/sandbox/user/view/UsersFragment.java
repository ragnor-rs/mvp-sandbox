package io.reist.sandbox.user.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import io.reist.sandbox.R;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.core.view.BaseFragment;
import io.reist.sandbox.user.UserFragmentComponent;
import io.reist.sandbox.user.presenter.UsersPresenter;

/**
 * Created by m039 on 11/12/15.
 */
public class UsersFragment extends BaseFragment<UsersPresenter>
    implements UsersView
{

    public static UsersFragment newInstance() {
        return newInstance(UsersFragment.class, R.layout.fragment_users);
    }

    @Inject
    UsersPresenter mPresenter;

    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;

    UsersAdapter mAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter = new UsersAdapter());

//        mAdapter.setOnUserClickListener(user -> getFragmentController().showFragment(RepoListFragment.newInstance(user)));
    }

    @NonNull
    @Override
    protected UsersPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(Object from) {
        ((UserFragmentComponent) from).inject(this);
    }

    @Override
    public void displayData(List<User> users) {
        mAdapter.setUsers(users);
    }
}
