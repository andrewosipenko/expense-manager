package com.es.jointexpensetracker.service.exceptions;

public class ExpenseGroupNotFoundException extends Exception {

    public ExpenseGroupNotFoundException() {
    }

    public ExpenseGroupNotFoundException(String message) {
        super(message);
    }

    public ExpenseGroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpenseGroupNotFoundException(Throwable cause) {
        super(cause);
    }

    public ExpenseGroupNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
