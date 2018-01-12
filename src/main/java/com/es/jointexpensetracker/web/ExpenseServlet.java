package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.exception.DataNotFoundException;
import com.es.jointexpensetracker.filter.NotificationFilter;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.ExpenseServiceSingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseServlet extends HttpServlet {

    private final static String EXPENSE_JSP_PATH = "/WEB-INF/pages/expense.jsp";
    private final static String SUCCESS_MESSAGE_TEMPLATE = "Expense '%s' was updated successfully";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ExpenseService service = ExpenseServiceSingleton.getInstance();
        try {
            Expense expense = loadExpense(service, request.getPathInfo());
            request.setAttribute(
                    "expense",
                    expense
            );
            request.getRequestDispatcher(EXPENSE_JSP_PATH).forward(request, response);
        } catch (DataNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ExpenseService service = ExpenseServiceSingleton.getInstance();
        try {
            Expense expense = loadExpense(service, request.getPathInfo());
            expense.setAmount(
                    new BigDecimal(request.getParameter("amount"))
            );
            expense.setDescription(
                    request.getParameter("description")
            );
            expense.setPerson(
                    request.getParameter("person")
            );
            expense.setDate(
                    LocalDate.parse(request.getParameter("date"))
            );
            ExpenseServiceSingleton.getInstance().save();

            request.setAttribute(
                    NotificationFilter.MESSAGE_KEY,
                    String.format(SUCCESS_MESSAGE_TEMPLATE, expense.getDescription())
            );

            response.sendRedirect(getServletContext().getContextPath() + "/expenses");
        } catch (DataNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Expense loadExpense(ExpenseService service, String pathInfo)
            throws DataNotFoundException{
        long expenseId = Long.parseLong(pathInfo.substring(1));
        return service.loadExpenseById(expenseId);
    }
}
