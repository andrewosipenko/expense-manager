package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        try{
            ExpenseService service = ExpenseService.getService();
            long id = Long.parseLong(pathInfo.substring(1));
            Expense expense = service.getExpenseById(id);
            if (expense == null)
                throw new NullPointerException();
            request.setAttribute("expense", expense);
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
        } catch (NumberFormatException | NullPointerException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: update expense
    }
}
