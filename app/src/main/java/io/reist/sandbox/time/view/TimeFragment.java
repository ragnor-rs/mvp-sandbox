package io.reist.sandbox.time.view;

import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import io.reist.sandbox.R;
import io.reist.sandbox.time.TimeComponent;
import io.reist.sandbox.time.presenter.TimePresenter;
import io.reist.visum.view.VisumFragment;

/**
 * Created by Reist on 20.05.16.
 */
public class TimeFragment extends VisumFragment<TimePresenter> implements TimeView {

    @Bind(R.id.time_text)
    TextView timeText;

    @Inject
    TimePresenter presenter;

    public TimeFragment() {
        super(TimePresenter.VIEW_ID_MAIN);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_time;
    }

    @Override
    public TimePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void inject(Object from) {
        ((TimeComponent) from).inject(this);
    }

    @Override
    public void showTime(Long t) {
        timeText.setText(Long.toString(t));
    }

    @OnClick(R.id.show_time_btn)
    void onShowTimeClicked() {
        presenter.onShowTimeClicked(getActivity());
    }

    @OnClick(R.id.hide_time_btn)
    void onHideTimeClicked() {
        presenter.onHideTimeClicked();
    }

}
