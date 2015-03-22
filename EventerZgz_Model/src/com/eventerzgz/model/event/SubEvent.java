package com.eventerzgz.model.event;

import com.eventerzgz.model.commons.Place;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;

/**
 * Created by joseluis on 21/3/15.
 */
public class SubEvent {

    @Element(name="lugar", required = false)
    @Path("lugar")
    private Place where;

    @Element(name="horario", required = false)
    private String when;

    @Element(name="comentarios", required = false)
    private String comment;

    public static Event doParse(String sRawObj){
        return new Event();
    }

    //GETTERS & SETTERS

    public Place getWhere() {
        return where;
    }

    public void setWhere(Place where) {
        this.where = where;
    }

    @Override
    public String toString() {
        return "SubEvent{" +
                "where=" + where +
                ", when='" + when + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
