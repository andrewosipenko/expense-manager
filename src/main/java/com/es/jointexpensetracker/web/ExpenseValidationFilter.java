package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.util.FormParser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseValidationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // validate URL
        String path = request.getPathInfo().substring(1);
        if (path.matches("[1-9]\\d{0,18}")){
            Expense expense = null;
            try{
                long id = Long.parseLong(path);
                expense = ExpenseService.getInstance().getExpenseById(id);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if (expense == null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("expense", expense);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // validate "update expense" form data
        if (request.getMethod().equalsIgnoreCase("post")){
            FormParser parser = new UpdateExpenseFormParser(request);
            if (!parser.isValid()){
                request.setAttribute("updateErrorMessage", parser.getErrorMessage());
                request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
                return;
            }
            request.setAttribute("updateParser", parser);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
