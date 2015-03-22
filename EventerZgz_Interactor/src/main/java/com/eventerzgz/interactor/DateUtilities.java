package com.eventerzgz.interactor;

import com.eventerzgz.model.Base;

import org.joda.time.DateTime;

/**
 * Created by Omar on 22/03/2015.
 */
public class DateUtilities {

    public static String getStartOfToday() {
        return new DateTime().withTime(0, 0, 0, 0).toString(Base.DATE_FORMAT);
    }
    public static String getEndOfToday() {
        return new DateTime().withTime(23, 59, 59, 999).toString(Base.DATE_FORMAT);
    }
    public static String getStartOfTomorrow() {
        return new DateTime().plusDays(1).withTime(0, 0, 0, 0).toString(Base.DATE_FORMAT);
    }
    public static String getEndOfTomorrow() {
        return new DateTime().plusDays(1).withTime(23, 59, 59, 999).toString(Base.DATE_FORMAT);
    }
    public static String getEndOfWeek() {
        return new DateTime().plusDays(6).withTime(23, 59, 59, 999).toString(Base.DATE_FORMAT);
    }
}
