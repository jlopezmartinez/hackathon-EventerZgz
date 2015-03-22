package com.eventerzgz.model.exception;

/**
 * Created by joseluis on 21/3/15.
 */
public class EventZgzException extends Exception {

    public EventZgzException() {
    }

    public EventZgzException(String message) {
        super(message);
    }

    public EventZgzException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventZgzException(Throwable cause) {
        super(cause);
    }

    public EventZgzException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
