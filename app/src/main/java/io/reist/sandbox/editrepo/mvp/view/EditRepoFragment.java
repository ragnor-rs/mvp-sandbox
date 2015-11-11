package io.reist.sandbox.editrepo.mvp.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import io.reist.sandbox.R;
import io.reist.sandbox.app.mvp.model.ResponseModel;
import io.reist.sandbox.app.mvp.view.widget.LoaderView;
import io.reist.sandbox.core.mvp.view.BaseFragment;
import io.reist.sandbox.editrepo.di.EditRepoComponent;
import io.reist.sandbox.editrepo.mvp.presenter.EditRepoPresenter;
import io.reist.sandbox.repos.mvp.model.Repo;

/**
 * Created by defuera on 10/11/2015.
 */
public class EditRepoFragment extends BaseFragment<EditRepoPresenter> implements EditRepoView {

    @Bind(R.id.repo_name)
    TextView repoName;

    @Bind(R.id.author_name)
    TextView authorName;

    @Bind(R.id.repo_url)
    TextView repoUrl;

    @Bind(R.id.loader)
    LoaderView loaderView;

    @Bind(R.id.repo_container)
    ViewGroup repoContainer;

    @Inject
    EditRepoPresenter presenter;

    public EditRepoFragment() {
        super(R.layout.fragment_edit_repo);
    }

    @Override
    protected void inject(Object from) {
        ((EditRepoComponent) from).inject(this);
    }

    @Override
    protected EditRepoPresenter getPresenter() {
        return presenter;
    }

    @OnClick(R.id.save)
    void onSaveButtonClick() {
        presenter.saveRepo(
                repoName.getText().toString(),
                authorName.getText().toString(),
                repoUrl.getText().toString());
    }

    @Override
    public void displayError(ResponseModel.Error error) {
        loaderView.showNetworkError();
    }

    @Override
    public void displayData(Repo repo) {
        repoName.setText(repo.name);
        authorName.setText(repo.author);
        repoUrl.setText(repo.url);
    }

    @Override
    public void showLoader(boolean show) {
        repoContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        loaderView.showLoading(show);
    }
}
