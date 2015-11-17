package io.reist.sandbox.repoedit.view;

import android.os.Bundle;

import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.model.Response;
import io.reist.sandbox.core.view.BaseView;

/**
 * Created by defuera on 11/11/2015.
 */
public interface RepoEditView extends BaseView {
    Bundle getArguments();

    void displayError(Response.Error error);

    void displayData(Repo data);

    void showLoader(boolean show);

    void back();
}
