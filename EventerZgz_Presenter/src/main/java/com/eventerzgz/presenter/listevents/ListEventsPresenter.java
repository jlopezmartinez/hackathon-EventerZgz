package com.eventerzgz.presenter.listevents;


import static com.eventerzgz.interactor.events.EventInteractor.EventFilter;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import com.eventerzgz.interactor.DateUtilities;
import com.eventerzgz.interactor.QueryBuilder;
import com.eventerzgz.interactor.category.CategoryInteractor;
import com.eventerzgz.interactor.events.EventInteractor;
import com.eventerzgz.interactor.population.PopulationInteractor;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.BasePresenter;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by JavierArroyo on 21/3/15.
 */
public class ListEventsPresenter extends BasePresenter {

    public final ListEventsIface listEventsIface;
    
    private static final String DEFAULT_SORT = QueryBuilder.FIELD.START_DATE + "," +QueryBuilder.FIELD.END_DATE;

    public ListEventsPresenter(ListEventsIface listEventsIface) {
        this.listEventsIface = listEventsIface;
    }

    public static Event eventDummy() {
        Event eventDummy = new Event();

        eventDummy.setdEndDate(new Date());
        eventDummy.setdLastUpdate(new Date());
        eventDummy.setsDescription("Description");
        eventDummy.setsTicketType("Libre");
        eventDummy.setsTitle("Titulo");
        return eventDummy;
    }

    public void getEventsByPopulations(String... populationIds) {
        QueryBuilder queryBuilder = new QueryBuilder().fromToday();
        if (populationIds != null && populationIds.length > 0) {
            boolean first = true;
            queryBuilder.and().group();
            for (String populationId : populationIds) {
                if (!first) {
                    queryBuilder.or();
                } else {
                    first = false;
                }
                queryBuilder.addFilter(QueryBuilder.FIELD.POPULATION, QueryBuilder.COMPARATOR.EQUALS, populationId);
            }
            queryBuilder.ungroup();
        }
        String query = queryBuilder.build();
        getEventList(EventFilter.createFilter(EventFilter.QUERY_FILTER, query),
                EventFilter.createFilter(EventFilter.SORT, DEFAULT_SORT));
    }

    public void getEventsToday() {
        getEventsByDateRange(DateUtilities.getStartOfToday(), DateUtilities.getEndOfToday());
    }

    public void getEventsTomorrow() {
        getEventsByDateRange(DateUtilities.getStartOfTomorrow(), DateUtilities.getEndOfTomorrow());
    }

    public void getEventsWeek() {
        getEventsByDateRange(DateUtilities.getStartOfToday(), DateUtilities.getEndOfWeek());
    }


    public void getEventsByUserPreferences(Context context){
        BasePresenter.getEventsByPreferencesInOtherThread(context,new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listEventsIface.error(e.getMessage());
            }

            @Override
            public void onNext(List<Event> events) {
                listEventsIface.fetchedEvents(events);
                onCompleted();
            }
        });
    }

    private void getEventsByDateRange(String start, String end) {
        String query = new QueryBuilder()
                .addFilter(QueryBuilder.FIELD.START_DATE, QueryBuilder.COMPARATOR.GREATER_EQUALS, start)
                .and().addFilter(QueryBuilder.FIELD.END_DATE, QueryBuilder.COMPARATOR.GREATER_EQUALS, end)
                .build();
        getEventList(EventFilter.createFilter(EventFilter.QUERY_FILTER, query),
                EventFilter.createFilter(EventFilter.SORT, DEFAULT_SORT));
    }

    public void getEventsByCategories(String... categoryIds) {
        QueryBuilder queryBuilder = new QueryBuilder().fromToday();
        if (categoryIds != null && categoryIds.length > 0) {
            boolean first = true;
            queryBuilder.and().group();
            for (String categoryId : categoryIds) {
                if (!first) {
                    queryBuilder.or();
                } else {
                    first = false;
                }
                queryBuilder.addFilter(QueryBuilder.FIELD.CATEGORY, QueryBuilder.COMPARATOR.EQUALS, categoryId);
            }
            queryBuilder.ungroup();
        }
        String query = queryBuilder.build();
        getEventList(EventFilter.createFilter(EventFilter.QUERY_FILTER, query),
                EventFilter.createFilter(EventFilter.SORT, DEFAULT_SORT));
    }

    public void getEventsByTitle(String title) {
        String query = new QueryBuilder().fromToday()
                .and().addFilter(QueryBuilder.FIELD.TITLE, QueryBuilder.COMPARATOR.EQUALS, "*" + title + "*")
                .build();
        getEventList(EventFilter.createFilter(EventFilter.QUERY_FILTER, query),
                EventFilter.createFilter(EventFilter.SORT, DEFAULT_SORT));
    }

    public void getAllEvents() {
        String query = new QueryBuilder().fromToday().build();
        getEventList(EventFilter.createFilter(EventFilter.QUERY_FILTER, query),
                EventFilter.createFilter(EventFilter.SORT, DEFAULT_SORT));
    }

    private void getEventsByMEGADEMOQUERY() {
        String query = new QueryBuilder()
                .addFilter(QueryBuilder.FIELD.START_DATE, QueryBuilder.COMPARATOR.GREATER_EQUALS, "2015-03-01T00:00:00Z")
                .addFilter(QueryBuilder.FIELD.END_DATE, QueryBuilder.COMPARATOR.GREATER_EQUALS, "2015-03-01T00:00:00Z")
                .addFilter(QueryBuilder.FIELD.CATEGORY, QueryBuilder.COMPARATOR.EQUALS, "37")
                .addFilter(QueryBuilder.FIELD.TITLE, QueryBuilder.COMPARATOR.EQUALS, "bib")
                .build();
        getEventList(
                EventFilter.createFilter(EventFilter.QUERY_FILTER, query),
                EventFilter.createFilter(EventFilter.START, 0),
                EventFilter.createFilter(EventFilter.SORT, "startDate desc"), // "desc" is optional
                EventFilter.createFilter(EventFilter.ROWS, 50),
                EventFilter.createFilter(EventFilter.DISTANCE, 3000), //metros
                EventFilter.createFilter(EventFilter.POINT, "-0.8830288063687367,41.62968403793101") // va de la mano de DISTANCE
        );
    }

    private void getEventList(final EventFilter... eventFilter) {

        observerTask(new Observable.OnSubscribe<List<Event>>() {
            @Override
            public void call(Subscriber suscriber) {
                try {
                    suscriber.onNext(EventInteractor.getAllEvent(eventFilter));
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                    suscriber.onError(e);
                }
            }
        }, new Subscriber<List<Event>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listEventsIface.error(e.getMessage());
            }

            @Override
            public void onNext(List<Event> o) {
                listEventsIface.fetchedEvents(o);
                onCompleted();
            }
        });

        observerTask(new Observable.OnSubscribe<List<Category>>() {
            @Override
            public void call(Subscriber suscriber) {
                try {
                    suscriber.onNext(CategoryInteractor.getCategories());
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                    suscriber.onError(e);
                }
            }
        }, new Subscriber<List<Category>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listEventsIface.error(e.getMessage());
            }

            @Override
            public void onNext(List<Category> o) {
                listEventsIface.fetchedCategories(o);
                onCompleted();
            }
        });

    }

    public void getPopulation(){

        observerTask(new Observable.OnSubscribe<List<Category>>() {
            @Override
            public void call(Subscriber suscriber) {
                try
                {
                    suscriber.onNext(PopulationInteractor.getPopulations());
                } catch (Exception e)
                {
                    Log.e(TAG, e.getMessage(), e);
                    suscriber.onError(e);
                }
            }
        }, new Subscriber<List<Population>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listEventsIface.error(e.getMessage());
            }
            @Override
            public void onNext(List<Population> o) {
                listEventsIface.fetchedPopulation(o);
                onCompleted();
            }
        });
    }

}
