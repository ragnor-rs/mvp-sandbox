package io.reist.sandbox.repos.mvp.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reist.sandbox.R;
import io.reist.sandbox.repos.mvp.model.Repo;

/**
 * Created by Reist on 10/14/15.
 */
public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {

    private final List<Repo> repos;

    @Inject
    LayoutInflater layoutInflater;

    public RepoListAdapter(List<Repo> repos) {
        this.repos = repos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.github_repo_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder c, int position) {
        c.textView.setText(repos.get(position).name);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.daggertest_repo_item_text_view)
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
