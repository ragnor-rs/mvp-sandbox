package io.reist.sandbox.repoedit.mvp.view;

import android.os.Bundle;

import io.reist.sandbox.app.mvp.model.ResponseModel;
import io.reist.sandbox.core.mvp.view.BaseView;
import io.reist.sandbox.repolist.mvp.model.Repo;

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
