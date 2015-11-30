package io.reist.sandbox.users.view;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.visum.model.Error;
import io.reist.visum.view.BaseView;

/**
 * Created by defuera on 05/11/2015.
 */
public interface UserReposView extends BaseView {
    void showLoader(boolean show);

    void displayError(Error error);

    void displayData(List<Repo> data);

    Long getUserId();
}
