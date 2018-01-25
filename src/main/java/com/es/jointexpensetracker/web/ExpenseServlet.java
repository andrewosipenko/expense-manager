package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class ExpenseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            int id = Integer.parseInt(request.getPathInfo().substring(1));
            if(id<1 || id>ExpenseService.getInstance().getExpenses().size())
            {
                throw new NumberFormatException();
            }
            request.setAttribute("expense", ExpenseService.getInstance().getExpense(id-1));
        }
        catch (NumberFormatException e)
        {
            request.setAttribute("exception","There is no such page :c");
            request.getRequestDispatcher("/WEB-INF/pages/page404.jsp").forward(request,response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: update expense
    }
}
