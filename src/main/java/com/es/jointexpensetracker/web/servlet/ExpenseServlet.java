package com.es.jointexpensetracker.web.servlet;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.web.service.MessageService;
import com.es.jointexpensetracker.web.service.ParseExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class ExpenseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            Long id = ParseExpenseService.getExpenseID(request);
            request.setAttribute("expense", ExpenseService.getInstance().getExpense(id));
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request,response);
        }
        catch (IllegalArgumentException e)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            Long id = ParseExpenseService.getExpenseID(request);
            Expense expense = ExpenseService.getInstance().getExpense(id);
            Expense parsedExpense = ParseExpenseService.parseExpenseData(request);
            ParseExpenseService.copyExpenseData(expense,parsedExpense);

            MessageService.sendMessage(request,MessageService.FLASH_MESSAGE,
                    "Expense \"" + expense.getDescription() + "\" was updated successfully");
            response.sendRedirect(request.getContextPath() + "/expenses");
        }
        catch (NumberFormatException e)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (IllegalArgumentException | DateTimeParseException e )
        {
            MessageService.sendMessage(request,MessageService.FLASH_MESSAGE,
                    "Update failed." + e.getMessage());
            response.sendRedirect(request.getRequestURL().toString());
        }
    }

}
