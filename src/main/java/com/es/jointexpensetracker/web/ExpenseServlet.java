package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.FlashMessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseServlet extends HttpServlet {
    private ExpenseService expenseService;
    private FlashMessageService messageService;

    @Override
    public void init() throws ServletException {
        expenseService = ExpenseService.getInstance();
        messageService = FlashMessageService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo().substring(1);
        if (path.equals("add")) {
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
            return;
        }
        Expense expense = getExpenseByUrlPath(path);
        if (expense != null) {
            request.setAttribute("expense", expense);
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo().substring(1);
        if (path.equals("add")) {
            processAddExpense(request, response);
            return;
        }
        Expense expense = getExpenseByUrlPath(path);
        if (expense == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (request.getParameter("update") != null)
            processUpdateExpense(request, response, expense);
        else if (request.getParameter("delete") != null)
            processDeleteExpense(request, response, expense);
        else
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void processAddExpense(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExpenseFormParser parser = new ExpenseFormParser(request);
        if (parser.isValid()) {
            Expense expense = expenseService.addExpense(parser.getPerson(), parser.getDescription(), parser.getAmount(), parser.getCurrency(), parser.getDate());
            setFlashMessageAndRedirect(request, response, request.getContextPath() + "/expenses",
                    "Expense '" + expense.getHelper().getDescription() + "' has been created successfully");
        } else {
            setFlashMessageAndRedirect(request, response, request.getRequestURI(), parser.getErrorMessage());
        }
    }

    private void processUpdateExpense(HttpServletRequest request, HttpServletResponse response, Expense expense) throws IOException {
        ExpenseFormParser parser = new ExpenseFormParser(request);
        if (parser.isValid()) {
            expense.update(parser.getPerson(), parser.getDescription(), parser.getAmount(), parser.getCurrency(), parser.getDate());
            setFlashMessageAndRedirect(request, response, request.getContextPath() + "/expenses",
                    "Expense '" + expense.getHelper().getDescription() + "' has been updated successfully");
        } else {
            setFlashMessageAndRedirect(request, response, request.getRequestURI(), parser.getErrorMessage());
        }
    }

    private void processDeleteExpense(HttpServletRequest request, HttpServletResponse response, Expense expense) throws IOException {
        expenseService.removeExpense(expense);
        setFlashMessageAndRedirect(request, response, request.getContextPath() + "/expenses",
                "Expense '" + expense.getHelper().getDescription() + "' has been deleted successfully");
    }

    private void setFlashMessageAndRedirect(HttpServletRequest request, HttpServletResponse response, String url, String message) throws IOException {
        messageService.putFlashMessage(request, message);
        response.sendRedirect(url);
    }

    private Expense getExpenseByUrlPath(String path) {
        if (path.matches("[1-9]\\d{0,18}")) {
            Expense expense = null;
            try {
                long id = Long.parseLong(path);
                expense = expenseService.getExpenseById(id);
            } catch (NumberFormatException ignored) {}
            return expense;
        }
        return null;
    }
}
