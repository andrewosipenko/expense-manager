package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExpenseService service = ExpenseService.getInstance();
        request.setAttribute("expenses", service.getExpenses());
        request.getRequestDispatcher("WEB-INF/pages/expenses.jsp").forward(request, response);
    }
}
