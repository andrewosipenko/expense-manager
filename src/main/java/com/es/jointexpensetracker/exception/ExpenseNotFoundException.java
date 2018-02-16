package com.es.jointexpensetracker.exception;


public class ExpenseNotFoundException extends Exception {

    public ExpenseNotFoundException(String message) {
        super(message);
    }

    public ExpenseNotFoundException() {
        super();
    }
}
