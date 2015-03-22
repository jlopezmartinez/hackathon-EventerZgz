package com.eventerzgz.interactor.events;

import java.util.Collections;
import java.util.List;

import com.eventerzgz.model.event.Event;

/**
 * Created by joseluis on 20/3/15.
 */
public class EventDb implements EventDatasource
{
    protected EventDb(){

    }


    @Override
    public void createEvent(Event objEvent)
    {

    }

    @Override
    public void updateEvent(Event objEvent)
    {

    }

    @Override
    public void deleteEvent(String sId)
    {

    }

    @Override
    public List<Event> getAllEvents(EventInteractor.EventFilter... eventFilter) {
        return null;
    }


    @Override
    public Event findEventById(String sId)
    {
        return null;
    }

}
