package com.es.expensetracker.web;

import com.es.expensetracker.model.Expense;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ExpenseListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("expenses", Arrays.asList(new Expense(), new Expense()));
        request.getRequestDispatcher("WEB-INF/pages/expenses.jsp").forward(request, response);
    }
}
