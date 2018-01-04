package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseServiceSingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class ExpenseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: show expense page

        String pathInfo = request.getPathInfo();
        if(Pattern.matches("/[1-9][0-9]*", pathInfo)){
            long expenseId = Long.valueOf(pathInfo.substring(1));
            Expense expense = ExpenseServiceSingleton.getInstance().getExpenseById(expenseId);
            if(expense != null){
                request.setAttribute(
                        "expense",
                        expense
                );
                request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: update expense
    }
}
