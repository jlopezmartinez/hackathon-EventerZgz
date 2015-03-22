package com.eventerzgz.model;

import org.simpleframework.xml.Element;

import java.text.DateFormat;

/**
 * Created by joseluis on 20/3/15.
 */
public abstract class Base
{

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Element(required = false)
    private String id;

    @Element(name="title", required = false)
    private String sTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }


    public String getFieldWithUri(String sFieldValue){
        return "http:"+sFieldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Base)) return false;

        Base base = (Base) o;

        if (id != null ? !id.equals(base.id) : base.id != null) return false;
        if (sTitle != null ? !sTitle.equals(base.sTitle) : base.sTitle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sTitle != null ? sTitle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\nBase{" +
                "id=" + id +
                ", sTitle='" + sTitle + '\'' +
                '}';
    }


}
