package com.eventerzgz.presenter.listevents;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenterIface;

import java.util.List;

/**
 * Created by JavierArroyo on 21/3/15.
 */
public interface ListEventsIface extends BasePresenterIface {

    public void fetchedEvents(List<Event> event);
    public void fetchedCategories(List<Category> event);
    public void fetchedPopulation(List<Population> populationList);
}
