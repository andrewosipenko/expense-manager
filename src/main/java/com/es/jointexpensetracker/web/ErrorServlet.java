package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.exception.DataNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorServlet extends HttpServlet {

    private final static String EXCEPTION_KEY = "javax.servlet.error.exception";
    private final static String ERROR_JSP_PATH = "/WEB-INF/pages/error.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Throwable exception = (Throwable)request.getAttribute(EXCEPTION_KEY);
        String message;
        int errorCode;
        
        if(exception instanceof DataNotFoundException){
            errorCode = HttpServletResponse.SC_NOT_FOUND;
            message = "Not found. Probably deleted or never existed";
        } else {
            errorCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            message = "Unknown server error.";
        }

        response.setStatus(errorCode);
        request.setAttribute(
                "errorCode",
                errorCode
        );
        request.setAttribute(
                "errorMessage",
                message
        );

        request.getRequestDispatcher(ERROR_JSP_PATH).forward(request, response);
    }
}
