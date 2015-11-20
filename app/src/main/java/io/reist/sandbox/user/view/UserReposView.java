package io.reist.sandbox.user.view;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.core.view.BaseView;

/**
 * Created by defuera on 05/11/2015.
 */
public interface UserReposView extends BaseView {
    void showLoader(boolean show);

    void displayError(Response.Error error);

    void displayData(List<Repo> data);

    Long getUserId();
}