package io.reist.sandbox.user.view;

import java.util.List;

import io.reist.sandbox.app.model.User;
import io.reist.sandbox.core.view.BaseView;


/**
 * Created by m039 on 11/12/15.
 */
public interface UsersView extends BaseView {

    void displayData(List<User> users);

}
