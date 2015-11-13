package io.reist.sandbox.repoedit.mvp.view;

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
import io.reist.sandbox.repoedit.di.RepoEditComponent;
import io.reist.sandbox.repoedit.mvp.presenter.RepoEditPresenter;
import io.reist.sandbox.repolist.mvp.model.Repo;

/**
 * Created by defuera on 10/11/2015.
 */
public class RepoEditFragment extends BaseFragment<RepoEditPresenter> implements RepoEditView {

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
    RepoEditPresenter presenter;

    public RepoEditFragment() {
        super(R.layout.fragment_edit_repo);
    }

    @Override
    protected void inject(Object from) {
        ((RepoEditComponent) from).inject(this);
    }

    @Override
    protected RepoEditPresenter getPresenter() {
        return presenter;
    }

    @OnClick(R.id.save)
    void onSaveButtonClick() {
        presenter.saveRepo(
                repoName.getText().toString(),
                authorName.getText().toString(),
                repoUrl.getText().toString());
    }

    @OnClick(R.id.delete)
    void onDeleteButtonClick() {
        presenter.deleteRepo();
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

    @Override
    public void back() {
        getFragmentManager().popBackStackImmediate();
    }
}
