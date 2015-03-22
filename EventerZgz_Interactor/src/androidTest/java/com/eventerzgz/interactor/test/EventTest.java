package com.eventerzgz.interactor.test;

import com.eventerzgz.interactor.events.EventInteractor;
import com.eventerzgz.model.exception.EventZgzException;


/**
 * Created by joseluis on 21/3/15.
 */
public class EventTest {


    public static void main(String[] args){
        try {
            EventInteractor.getInstance().getAllEvent(null);
        } catch (EventZgzException e) {
            e.printStackTrace();
        }
    }


}
