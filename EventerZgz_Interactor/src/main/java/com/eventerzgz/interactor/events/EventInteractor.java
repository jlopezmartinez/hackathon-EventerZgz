package com.eventerzgz.interactor.events;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.eventerzgz.interactor.BaseInteractor;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.model.exception.EventZgzException;


/**
 * Created by joseluis on 20/3/15.
 */
public class EventInteractor extends BaseInteractor{

    public static enum EventFilter{

        QUERY_FILTER("q") {
            @Override
            public void appendParam(StringBuilder stringBuilder) {

                String s = "";

                try {
                    s = URLEncoder.encode(getFilterValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("&").append(getKeyValue()).append("=").append(s);
            }
        },
        START ("start"){
            @Override
            public void appendParam(StringBuilder stringBuilder) {
                String s = "";

                try {
                    s = URLEncoder.encode(getFilterValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("&").append(getKeyValue()).append("=").append(s);
            }
        },
        SORT ("sort"){
            @Override
            public void appendParam(StringBuilder stringBuilder) {
                String s = "";

                try {
                    s = URLEncoder.encode(getFilterValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("&").append(getKeyValue()).append("=").append(s);
            }
        },
        ROWS ("rows"){
            @Override
            public void appendParam(StringBuilder stringBuilder) {
                String s = "";

                try {
                    s = URLEncoder.encode(getFilterValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("&").append(getKeyValue()).append("=").append(s);
            }
        },
        DISTANCE ("distance"){
            @Override
            public void appendParam(StringBuilder stringBuilder) {
                String s = "";

                try {
                    s = URLEncoder.encode(getFilterValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("&").append(getKeyValue()).append("=").append(s);
            }
        },
        POINT ("point"){
            @Override
            public void appendParam(StringBuilder stringBuilder) {
                String s = "";

                try {
                    s = URLEncoder.encode(getFilterValue(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                stringBuilder.append("&").append(getKeyValue()).append("=").append(s);
            }
        };

        private String keyValue;
        private String filterValue;

        EventFilter(String s) {
            this.keyValue = s;
        }


        public static EventFilter createFilter(EventFilter eventFilter,String sValue){
            eventFilter.setFilterValue(sValue);
            return eventFilter;
        }
        public static EventFilter createFilter(EventFilter eventFilter,int iValue){
            eventFilter.setFilterValue(String.valueOf(iValue));
            return eventFilter;
        }

        public abstract void appendParam(StringBuilder stringBuilder);

        public String getKeyValue() {
            return keyValue;
        }

        private void setFilterValue(String filterValue) {
            this.filterValue = filterValue;
        }

        public String getFilterValue() {
            return filterValue;
        }


    }

    public void createEvent(Event objEvent)
    {

    }

    public void updateEvent(Event objEvent)
    {

    }

    public void deleteEvent(String sId)
    {

    }

    public static List<Event> getAllEvent(EventFilter... eventFilter) throws EventZgzException
    {
        //Decidir la impl
        return new EventerRest().getAllEvents(eventFilter);

    }

    public void findById(String sId){

    }



}
