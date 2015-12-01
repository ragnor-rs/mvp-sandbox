package io.reist.sandbox.repos.view;

import io.reist.sandbox.app.model.Repo;
import io.reist.visum.model.Error;
import io.reist.visum.view.BaseView;

/**
 * Created by defuera on 11/11/2015.
 */
public interface RepoEditView extends BaseView {

    void displayError(Error error);

    void displayData(Repo data);

    void showLoader(boolean show);

    void back();
}
