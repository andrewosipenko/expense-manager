package com.es.jointexpensetracker.web.exceptions;


public class HttpNotFoundException extends RuntimeException {

    public HttpNotFoundException() {
    }

    public HttpNotFoundException(String message) {
        super(message);
    }

    public HttpNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpNotFoundException(Throwable cause) {
        super(cause);
    }

    public HttpNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
