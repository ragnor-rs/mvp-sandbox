package io.reist.sandbox.editrepo.mvp.view;

import io.reist.sandbox.R;
import io.reist.sandbox.core.mvp.view.BaseFragment;
import io.reist.sandbox.editrepo.di.EditRepoComponent;
import io.reist.sandbox.editrepo.mvp.presenter.EditRepoPresenter;

/**
 * Created by defuera on 10/11/2015.
 */
public class EditRepoFragment extends BaseFragment<EditRepoPresenter> {

    public EditRepoFragment() {
        super(R.layout.fragment_edit_repo);
    }

    @Override
    protected void inject(Object from) {
        ((EditRepoComponent) from).inject(this);
    }

    @Override
    protected EditRepoPresenter getPresenter() {
        return new EditRepoPresenter();
    }
}
