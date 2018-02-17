package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.expenses.ExpenseService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpenseGroupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Pattern pattern = Pattern.compile("/(\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12})(.*)");
        Matcher matcher = pattern.matcher(request.getPathInfo());
        if (!matcher.matches()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        ExpenseService expenseService = ExpenseService.getInstance(UUID.fromString(matcher.group(1)));
        if (expenseService == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("expenseService", expenseService);

        String contextPath = request.getContextPath() + request.getServletPath() + "/" + matcher.group(1);
        request = new RewriteContextPathRequestWrapper(request, contextPath);

        String path = matcher.group(2);
        if (path.equals("")){
            response.sendRedirect(contextPath + "/expenses");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(matcher.group(2));
        if (requestDispatcher == null)
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        else
            requestDispatcher.forward(request, response);
    }

    private static class RewriteContextPathRequestWrapper extends HttpServletRequestWrapper {
        private String contextPath;

        private RewriteContextPathRequestWrapper(HttpServletRequest request, String contextPath) {
            super(request);
            this.contextPath = contextPath;
        }

        @Override
        public String getContextPath() {
            return contextPath;
        }

        @Override
        public String getRequestURI() {
            String servletPath = getServletPath();
            String pathInfo = getPathInfo();
            return contextPath + servletPath + (pathInfo == null ? "" : pathInfo);
        }
    }
}
