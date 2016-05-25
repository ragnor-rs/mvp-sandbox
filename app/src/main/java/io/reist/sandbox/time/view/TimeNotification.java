package io.reist.sandbox.time.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.NotificationCompat;

import java.util.Date;

import javax.inject.Inject;

import io.reist.sandbox.R;
import io.reist.sandbox.app.SandboxApplication;
import io.reist.sandbox.app.view.MainActivity;
import io.reist.sandbox.time.TimeComponent;
import io.reist.sandbox.time.presenter.TimePresenter;
import io.reist.visum.view.VisumBaseView;

/**
 * This class is a part of the example of single presenter - multiple views feature of Visum.
 *
 * @see TimeFragment
 * @see TimePresenter
 *
 * Created by Reist on 20.05.16.
 */
public class TimeNotification extends VisumBaseView<TimePresenter> implements TimeView {

    private static final int NOTIFICATION_ID = 1;

    @Inject
    TimePresenter presenter;

    @Inject
    SandboxApplication application;

    public TimeNotification(Context context) {
        super(TimePresenter.VIEW_ID_NOTIFICATION, context);
    }

    @Override
    public void showTime(Long t) {

        NotificationManager notificationManager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(application, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(application, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(application)
                .setContentTitle(application.getString(R.string.app_name))
                .setContentText(application.getString(R.string.current_time, new Date(t)))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setSmallIcon(android.R.drawable.ic_menu_agenda)
                .build();
        notificationManager.notify(NOTIFICATION_ID, notification);

    }

    @Override
    public TimePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void inject(@NonNull Object from) {
        ((TimeComponent) from).inject(this);
    }

    @Override
    public void detachPresenter() {

        super.detachPresenter();

        NotificationManager notificationManager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.cancel(NOTIFICATION_ID);

    }

}
