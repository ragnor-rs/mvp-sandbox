package io.reist.sandbox.repoedit.view;

import io.reist.sandbox.app.model.Repo;
import io.reist.visum.view.BaseView;

/**
 * Created by defuera on 11/11/2015.
 */
public interface RepoEditView extends BaseView {

    void displayError(io.reist.visum.Error error);

    void displayData(Repo data);

    void showLoader(boolean show);

    void back();
}
