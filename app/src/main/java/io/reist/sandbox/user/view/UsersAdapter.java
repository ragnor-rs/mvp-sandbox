package io.reist.sandbox.user.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reist.sandbox.R;
import io.reist.sandbox.app.model.User;

/**
 * Created by m039 on 11/12/15.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>
        implements View.OnClickListener {

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    private List<User> mUsers = new ArrayList<>();
    private OnUserClickListener mOnUserClickListener;

    public UsersAdapter() {
    }

    public void setUsers(List<User> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    public void setOnUserClickListener(OnUserClickListener l) {
        mOnUserClickListener = l;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        User user = mUsers.get(position);

        vh.name.setText(user.getName());

        vh.itemView.setTag(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = layoutInflater.inflate(R.layout.item_user, parent, false);
        ViewHolder vh = new ViewHolder(v);

        vh.itemView.setOnClickListener(this);

        return vh;
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();

        if (mOnUserClickListener != null && position != null) {
            mOnUserClickListener.onUserClick(mUsers.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (mUsers != null) ? mUsers.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.name)
        TextView name;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
