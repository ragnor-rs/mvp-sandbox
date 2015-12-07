package io.reist.sandbox.repos.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import io.reist.sandbox.R;
import io.reist.sandbox.app.model.Repo;
import io.reist.sandbox.app.view.widget.LoaderView;
import io.reist.sandbox.repos.ReposComponent;
import io.reist.sandbox.repos.presenter.RepoEditPresenter;
import io.reist.visum.model.Error;
import io.reist.visum.view.BaseFragment;

/**
 * Created by defuera on 10/11/2015.
 */
public class RepoEditFragment extends BaseFragment<RepoEditPresenter> implements RepoEditView {

    private static final String EXTRA_REPO_ID = "io.reist.sandbox.extra_repo_id";

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

    public static BaseFragment newInstance(Long userId) {
        RepoEditFragment fragment = new RepoEditFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_REPO_ID, userId);

        fragment.setArguments(bundle);
        return fragment;
    }

    public RepoEditFragment() {
        super(R.layout.fragment_edit_repo);
    }

    @Override
    protected void inject(Object from) {
        ((ReposComponent) from).inject(this);
    }

    @Override
    public RepoEditPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void ready() {

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
    public void displayError(Error error) {
        loaderView.showNetworkError();
    }

    @Override
    public void displayData(Repo repo) {
        repoName.setText(repo.name);
        authorName.setText(repo.owner.name);
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

    @Override
    public long getRepoId() {
        return getArguments().getLong(EXTRA_REPO_ID);
    }

}
