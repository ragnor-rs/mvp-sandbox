package io.reist.sandbox.user.view;

import java.util.List;

import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.app.model.User;
import io.reist.sandbox.core.view.BaseView;


/**
 * Created by m039 on 11/12/15.
 */
public interface UsersView extends BaseView {

    void showLoader(boolean show);

    void displayError(Response.Error error);

    void displayData(List<User> users);

}
