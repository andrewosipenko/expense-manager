package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteExpenseServlet extends ExpenseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            Long id = getExpenseID(req);
            Expense expense = ExpenseService.getInstance().getExpense(id);
            ExpenseService.getInstance().deleteExpense(id);

            HttpSession session = req.getSession(false);
            session.setAttribute("flashMessage", "Expense " + expense.getDescription() + " was deleted successfully");

            resp.sendRedirect(req.getContextPath() + "/expenses");
        }
        catch (IllegalArgumentException e)
        {
            resp.sendError(resp.SC_BAD_REQUEST,e.getMessage());
        }

    }
}
