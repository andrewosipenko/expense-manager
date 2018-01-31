package com.es.jointexpensetracker.exception;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseNotFoundException extends Exception {

    public ExpenseNotFoundException(String message) {
        super(message);
    }

    public ExpenseNotFoundException() {
        super();
    }
}
