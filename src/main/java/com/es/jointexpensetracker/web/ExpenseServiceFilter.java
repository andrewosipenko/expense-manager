package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.expenses.ExpenseService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseServiceFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object obj = request.getAttribute("expenseService");
        if (obj == null || !(obj instanceof ExpenseService))
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        else
            filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
