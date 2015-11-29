package io.reist.sandbox.users.presenter;

import java.util.List;

import javax.inject.Inject;

import io.reist.sandbox.app.model.User;
import io.reist.sandbox.users.model.UserService;
import io.reist.sandbox.users.view.UserListView;
import io.reist.visum.model.Response;
import io.reist.visum.presenter.BasePresenter;
import rx.Observer;

/**
 * Created by m039 on 11/12/15.
 */
public class UserListPresenter extends BasePresenter<UserListView> {

    UserService mUserService;
    private boolean mIsDataLoaded = false;

    @Inject
    UserListPresenter(UserService userService) {
        mUserService = userService;
    }

    @Override
    protected void onViewAttached() {
        mIsDataLoaded = false;

        view().showLoader(true);
        loadData();
    }

    public void loadData() {
        subscribe(mUserService.list(), new UsersObserver());
    }

    /**
     * Used in test only
     */
    public boolean isDataLoaded() {
        return mIsDataLoaded;
    }

    private class UsersObserver implements Observer<Response<List<User>>> {

        @Override
        public void onNext(Response<List<User>> response) {
            UserListView view = view();
            if (response.isSuccessful()) {
                view.displayData(response.getResult());
                view.showLoader(false);
                mIsDataLoaded = true;
            } else {
                view.displayError(response.getError());
            }

        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            view().showLoader(false);
        }

    }
}
