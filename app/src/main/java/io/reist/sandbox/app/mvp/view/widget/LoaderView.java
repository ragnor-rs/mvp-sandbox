package io.reist.sandbox.app.mvp.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reist.sandbox.R;

/**
 * Created by defuera on 10/11/2015.
 */
public class LoaderView extends LinearLayout { //cur is this right package for this class?

    @Bind(R.id.progress_bar)
    View progressBar;

    @Bind(R.id.message)
    View message;

    public LoaderView(Context context) {
        super(context);
        init();
    }

    public LoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.view_loader, this);
        ButterKnife.bind(this);
    }

    public void showLoading(boolean show) {
        setVisibility(show ? VISIBLE : GONE);
        message.setVisibility(GONE);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void showNetworkError() {
        setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        message.setVisibility(VISIBLE);
    }
}
