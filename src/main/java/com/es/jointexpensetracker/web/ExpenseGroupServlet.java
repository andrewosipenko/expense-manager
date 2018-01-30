package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.ExpenseServiceSingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseGroupServlet extends HttpServlet {

    private static final int UUID_LENGTH = 36;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo().equals("/add") && request.getMethod().equals("GET")) {
            doGet(request, response);
        } else {
            String expenseGroup = request.getPathInfo().substring(1, UUID_LENGTH + 1);
            String forwardServletPath = request.getPathInfo().substring(1 + UUID_LENGTH);
            request.setAttribute(
                    "expenseGroupPath",
                    getExpenseGroupPath(expenseGroup, request)
            );

            String dispatchTo = String.format("%s?expenseGroup=%s", forwardServletPath, expenseGroup);
            request.getRequestDispatcher(dispatchTo).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ExpenseService expenseService = ExpenseServiceSingleton.getInstance();
        String expenseGroup = expenseService.createExpenseGroup();
        String redirectTo = getExpenseGroupPath(expenseGroup, request) + "/expenses";
        response.sendRedirect(redirectTo);
    }

    private String getExpenseGroupPath(String expenseGroup, HttpServletRequest request){
        return request.getContextPath()+request.getServletPath()+"/"+expenseGroup;
    }
}
