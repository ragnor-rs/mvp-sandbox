package io.reist.sandbox.time.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reist.sandbox.time.model.TimeService;
import io.reist.sandbox.time.view.TimeNotification;
import io.reist.sandbox.time.view.TimeView;
import io.reist.visum.presenter.VisumPresenter;

/**
 * Created by Reist on 20.05.16.
 */
public class TimePresenter extends VisumPresenter<TimeView> {

    public static final int VIEW_ID_MAIN = 1;
    public static final int VIEW_ID_NOTIFICATION = 2;

    private final TimeService timeService;

    @Inject
    public TimePresenter(TimeService timeService) {
        this.timeService = timeService;
    }

    @Override
    protected void onViewAttached(int id, @NonNull TimeView view) {
        subscribe(id, timeService.getTime(), view::showTime);
    }

    public void onShowTimeClicked(Context context) {
        attach(VIEW_ID_NOTIFICATION, TimeNotification.class, context);
    }

    public void onHideTimeClicked() {
        detach(VIEW_ID_NOTIFICATION);
    }

}
