package com.eventerzgz.presenter.events;

import android.util.Log;

import com.eventerzgz.interactor.events.EventInteractor;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenter;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by joseluis on 20/3/15.
 */
public class EventsPresenter extends BasePresenter
{
    public final EventsIface eventsIface;

    public EventsPresenter(EventsIface eventsIface)
    {
        this.eventsIface = eventsIface;
    }



    public void createEvent(String sName,String sDescription){

        final Event objEvent = new Event();

        observerTask(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber suscriber) {
                try
                {
                   // EventInteractor.getInstance().getAllEvent(null);
                    Log.i(TAG, "Event created!!");

                    suscriber.onCompleted();
                } catch (Exception e)
                {
                    Log.e(TAG, e.getMessage(), e);
                    suscriber.onError(e);
                }
            }
        }, new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                eventsIface.createdEvent();
            }

            @Override
            public void onError(Throwable e) {
                eventsIface.error(e.getMessage());
            }

            @Override
            public void onNext(Boolean o) {

            }
        });


    }


}
