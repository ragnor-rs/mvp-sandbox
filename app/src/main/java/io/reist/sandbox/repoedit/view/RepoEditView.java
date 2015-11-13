package io.reist.sandbox.repoedit.view;

import android.os.Bundle;

import io.reist.sandbox.app.model.ResponseModel;
import io.reist.sandbox.core.view.BaseView;
import io.reist.sandbox.repolist.model.Repo;

/**
 * Created by defuera on 11/11/2015.
 */
public interface RepoEditView extends BaseView {
    Bundle getArguments();

    void displayError(ResponseModel.Error error);

    void displayData(Repo data);

    void showLoader(boolean show);

    void back();
}
