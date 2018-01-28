package com.es.jointexpensetracker.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseGroupServlet extends HttpServlet {

    private static final int UUID_LENGTH = 36;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String expenseGroup = request.getPathInfo().substring(1,UUID_LENGTH+1);
        String forwardServletPath = request.getPathInfo().substring(1+UUID_LENGTH);
        request.setAttribute(
                "expenseGroupPath",
                request.getContextPath()+request.getServletPath()+"/"+expenseGroup
                );

        String dispatchTo = String.format("%s?expenseGroup=%s", forwardServletPath, expenseGroup);
        request.getRequestDispatcher(dispatchTo).forward(request, response);
    }
}
