package com.es.jointexpensetracker.web.servlet;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.web.service.MessageService;
import com.es.jointexpensetracker.web.service.ParseExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class AddExpenseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            Expense newExpense = ParseExpenseService.parseExpenseData(request);
            ExpenseService.getInstance().addExpense(newExpense);

            MessageService.sendMessage(request,MessageService.FLASH_MESSAGE,
                    "Expense "+newExpense.getDescription()+" was added successfully");
            response.sendRedirect(request.getContextPath()+"/expenses");
        }
        catch (NumberFormatException e)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (IllegalArgumentException | DateTimeParseException e )
        {
            HttpSession session = request.getSession(true);
            session.setAttribute("flashMessage", "Add failed. "+ e.getMessage());
            response.sendRedirect(request.getRequestURL().toString());
        }
    }
}
