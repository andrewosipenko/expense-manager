package com.es.jointexpensetracker.web.servlet;


import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.web.service.ExpenseGroupUUIDService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ExpenseListServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute(
            "expenses", ExpenseService.getInstance().getExpenses(ExpenseGroupUUIDService.getCurrentUUID(request)));
        request.getRequestDispatcher("WEB-INF/pages/expenses.jsp").forward(request, response);
    }
}