package io.reist.sandbox.repos.mvp.view;

import java.util.List;

import io.reist.sandbox.app.mvp.model.ResponseModel;
import io.reist.sandbox.core.mvp.view.BaseView;
import io.reist.sandbox.repos.mvp.model.Repo;

/**
 * Created by defuera on 05/11/2015.
 */
public interface RepoListView extends BaseView {
    void showLoader(boolean show);

    void displayError(ResponseModel.Error error);

    void displayData(List<Repo> data);
}
