package io.reist.sandbox.user.presenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reist.sandbox.app.model.User;
import io.reist.sandbox.user.model.UserService;
import io.reist.sandbox.user.view.UsersView;
import io.reist.visum.model.Response;
import io.reist.visum.presenter.BasePresenter;
import rx.Observer;

/**
 * Created by m039 on 11/12/15.
 */
@Singleton
public class UsersPresenter extends BasePresenter<UsersView> {

    UserService mUserService;
    private boolean mIsDataLoaded = false;

    @Inject
    UsersPresenter(UserService userService) {
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
            UsersView view = view();
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
