package com.es.jointexpensetracker.web.servlet;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.web.service.ExpenseGroupUUIDService;
import com.es.jointexpensetracker.web.service.MessageService;
import com.es.jointexpensetracker.web.service.ParseExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteExpenseServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            String currentUUID = ExpenseGroupUUIDService.getCurrentUUID(req);
            Long id = ParseExpenseService.getExpenseID(req);
            Expense expense = ExpenseService.getInstance().getExpense(currentUUID, id);
            ExpenseService.getInstance().deleteExpense(currentUUID, id);

            MessageService.sendMessage(req,MessageService.FLASH_MESSAGE,
                    "Expense " + expense.getDescription() + " was deleted successfully");
            resp.sendRedirect(req.getContextPath() + ExpenseGroupUUIDService.getFlagWithCurrentUUID(req) + "/expenses");
        }
        catch (IllegalArgumentException e)
        {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
        }
    }
}