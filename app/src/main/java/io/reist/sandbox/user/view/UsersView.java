package io.reist.sandbox.user.view;

import java.util.List;

import io.reist.sandbox.app.model.User;
import io.reist.visum.model.Error;
import io.reist.visum.view.BaseView;

/**
 * Created by m039 on 11/12/15.
 */
public interface UsersView extends BaseView {

    void showLoader(boolean show);

    void displayError(Error error);

    void displayData(List<User> users);

}
