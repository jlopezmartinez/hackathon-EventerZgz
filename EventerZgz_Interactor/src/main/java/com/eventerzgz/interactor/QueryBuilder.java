package com.eventerzgz.interactor;

import android.util.Log;

import com.eventerzgz.model.Base;
import com.eventerzgz.model.exception.EventZgzException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by joseluis on 21/3/15.
 */
public class QueryBuilder {

    private StringBuffer query = new StringBuffer();

    public enum COMPARATOR{
        EQUALS("=="),
        LESSER_EQUALS("=le="),
        LESSER("=lt="),
        GREATER_EQUALS("=le="),
        GREATER("=lt="),
        NOT_EQUALS("!=");

        private final String value;

        private COMPARATOR(final String newValue) {
            value = newValue;
        }

        public String toString() { return value; }
    }

    public enum FIELD{
        LAST_UPDATED("lastUpdated"),
        START_DATE("startDate"),
        END_DATE("endDate"),
        CATEGORY("subtemas.id"),
        POPULATION("poblacion.id"),
        TITLE("title");

        private final String value;

        private FIELD(final String newValue) {
            value = newValue;
        }

        public String toString() { return value; }
    }

    public QueryBuilder addFilter(FIELD key, COMPARATOR comparator, String value) {
        query.append(key).append(comparator).append(value);
        return this;
    }


    public QueryBuilder and() {
        query.append(';');
        return this;
    }
    public QueryBuilder or() {
        query.append(',');
        return this;
    }
    public QueryBuilder group() {
        query.append('(');
        return this;
    }
    public QueryBuilder ungroup() {
        query.append(')');
        return this;
    }

    public QueryBuilder fromToday() {
        String today = DateUtilities.getStartOfToday();
        this.addFilter(QueryBuilder.FIELD.START_DATE, QueryBuilder.COMPARATOR.GREATER_EQUALS, today);
        this.and().addFilter(QueryBuilder.FIELD.END_DATE, QueryBuilder.COMPARATOR.GREATER_EQUALS, today);
        return this;
    }
    public String build() {
        return query.toString();
    }

}
