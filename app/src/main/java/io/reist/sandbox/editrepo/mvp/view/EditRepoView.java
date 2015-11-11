package io.reist.sandbox.editrepo.mvp.view;

import android.os.Bundle;

import io.reist.sandbox.app.mvp.model.ResponseModel;
import io.reist.sandbox.core.mvp.view.BaseView;
import io.reist.sandbox.repos.mvp.model.Repo;

/**
 * Created by defuera on 11/11/2015.
 */
public interface EditRepoView extends BaseView {
    Bundle getArguments();

    void displayError(ResponseModel.Error error);

    void displayData(Repo data);

    void showLoader(boolean show);
}
