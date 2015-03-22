package com.eventerzgz.presenter.service;

import static com.eventerzgz.interactor.events.EventInteractor.EventFilter;

import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import com.eventerzgz.interactor.QueryBuilder;
import com.eventerzgz.interactor.events.EventInteractor;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by joseluis on 21/3/15.
 */
public class AlarmReciver  extends BroadcastReceiver{

    private final String TAG = "AlarmReciver";
    private static AlarmIface alarmIface;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        //context.startService(new Intent(context, EventService.class));

        if (intent != null && intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            AlarmReciver.setAlarm(context,alarmIface);

        }


        BasePresenter.getEventsByPreferencesInOtherThread(context, new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {
                AlarmReciver.setAlarm(context, alarmIface);
            }

            @Override
            public void onError(Throwable e) {
                AlarmReciver.setAlarm(context, alarmIface);
            }

            @Override
            public void onNext(List<Event> events) {

                if (events != null && events.size() > 0) {

                    if (true) {
                        Event event = events.get(0);
                        if (alarmIface != null)
                            alarmIface.deliverNotification(context, event.getsTitle(), event.getId(), event);

                    } else {
                        if (alarmIface != null)
                            alarmIface.deliverNotification(context, " Tienes " + events.size() + " eventos nuevos", events.size() + "", null);
                    }

                }

                onCompleted();
            }
        });





    }



    public static void setAlarm(Context context,AlarmIface notIface)
    {

        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReciver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                30 * 1000, alarmIntent);

        if(notIface != null) alarmIface = notIface;

    }


}
