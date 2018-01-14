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
    private final static String SUCCESS_MESSAGE_TEMPLATE = "Expense '%s' was %s successfully";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ExpenseService service = ExpenseServiceSingleton.getInstance();
        try {
            Expense expense = loadExpense(service, request.getPathInfo().substring(1));
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
            switch (request.getPathInfo()) {
                case "/delete":
                    delete(request, service);
                    break;
                default:
                    update(request, service);
                    break;
            }
            ExpenseServiceSingleton.getInstance().save();
            response.sendRedirect(getServletContext().getContextPath() + "/expenses");
        } catch (DataNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void delete(HttpServletRequest request, ExpenseService service) throws DataNotFoundException{
        Expense expense = loadExpense(service, request.getParameter("id"));
        service.removeExpense(expense);
        attachMessage(
                request,
                String.format(SUCCESS_MESSAGE_TEMPLATE, expense.getDescription(), "deleted")
        );
    }

    private void update(HttpServletRequest request, ExpenseService service) throws DataNotFoundException{
        Expense expense = loadExpense(service, request.getPathInfo().substring(1));
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
        attachMessage(
                request,
                String.format(SUCCESS_MESSAGE_TEMPLATE, expense.getDescription(), "updated")
        );
    }

    private Expense loadExpense(ExpenseService service, String id)
            throws DataNotFoundException{
        long expenseId = Long.parseLong(id);
        return service.loadExpenseById(expenseId);
    }

    private void attachMessage(HttpServletRequest request, String message){
        request.setAttribute(
                NotificationFilter.MESSAGE_KEY,
                message);
    }
}
