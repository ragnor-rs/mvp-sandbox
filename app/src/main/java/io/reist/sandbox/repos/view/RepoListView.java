package io.reist.sandbox.repos.view;

import java.util.List;

import io.reist.sandbox.app.model.Repo;
import io.reist.visum.view.BaseView;

/**
 * Created by defuera on 05/11/2015.
 */
public interface RepoListView extends BaseView {
    void showLoader(boolean show);

    void displayError(io.reist.visum.Error error);

    void displayData(List<Repo> data);
}
