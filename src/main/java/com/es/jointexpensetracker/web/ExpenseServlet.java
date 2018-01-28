package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.util.SessionOneTimeMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UpdateExpenseFormParser parser = (UpdateExpenseFormParser) request.getAttribute("updateParser");
        Expense expense = (Expense) request.getAttribute("expense");

        expense.setPerson(parser.getPerson());
        expense.setDescription(parser.getDescription());
        expense.setDate(parser.getDate());
        expense.setAmount(parser.getAmount());
        expense.setCurrency(parser.getCurrency());

        new SessionOneTimeMessage(request.getSession(), "updateSuccessMessage", "Expense '"+expense.getDescription()+"' has been updated successfully").set();
        response.sendRedirect(request.getContextPath() + "/expenses");
    }
}
