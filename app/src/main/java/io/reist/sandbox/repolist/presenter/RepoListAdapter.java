package io.reist.sandbox.repolist.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reist.sandbox.R;
import io.reist.sandbox.repolist.model.Repo;

/**
 * Created by Reist on 10/14/15.
 */
public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {

    private final List<Repo> repos;
    private ItemClickListener itemClickListener;

    public RepoListAdapter(List<Repo> repos) {
        this.repos = repos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.github_repo_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder c, final int position) {
        c.textView.setText(repos.get(position).name);
        c.textView.setBackground(c.textView.getContext().getResources().getDrawable(R.drawable.list_item_clicked));
        c.textView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.itemClicked(repos.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.daggertest_repo_item_text_view)
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface ItemClickListener {

        void itemClicked(Repo repo);
    }

}
