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
        FlashMessageService messageService = FlashMessageService.getInstance();
        if (path.equals("add")){
            if (request.getParameter("add") != null) {
                ExpenseFormParser parser = new ExpenseFormParser(request);
                if (parser.isValid()) {
                    ExpenseService expenseService = ExpenseService.getInstance();
                    Expense expense = expenseService.addExpense(parser.getPerson(), parser.getDescription(), parser.getAmount(), parser.getCurrency(), parser.getDate());
                    messageService.putFlashMessage(request.getSession(), "operationSuccessMessage",
                            "Expense '" + expense.getHelper().getDescription() + "' has been created successfully");
                    response.sendRedirect(request.getContextPath() + "/expenses");
                } else {
                    messageService.putFlashMessage(request.getSession(), "formErrorMessage", parser.getErrorMessage());
                    response.sendRedirect(request.getRequestURI());
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            return;
        }
        Expense expense = getExpenseByUrlPath(path);
        if (expense != null){
            if (request.getParameter("update") != null){
                ExpenseFormParser parser = new ExpenseFormParser(request);
                if (parser.isValid()){
                    expense.update(parser.getPerson(), parser.getDescription(), parser.getAmount(), parser.getCurrency(), parser.getDate());
                    messageService.putFlashMessage(request.getSession(), "operationSuccessMessage",
                            "Expense '" + expense.getHelper().getDescription() + "' has been updated successfully");
                    response.sendRedirect(request.getContextPath() + "/expenses");
                } else {
                    messageService.putFlashMessage(request.getSession(), "formErrorMessage", parser.getErrorMessage());
                    response.sendRedirect(request.getRequestURI());
                }
            } else if (request.getParameter("delete") != null){
                ExpenseService expenseService = ExpenseService.getInstance();
                expenseService.removeExpense(expense);
                messageService.putFlashMessage(request.getSession(), "operationSuccessMessage",
                        "Expense '" + expense.getHelper().getDescription() + "' has been deleted successfully");
                response.sendRedirect(request.getContextPath() + "/expenses");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private Expense getExpenseByUrlPath(String path){
        if (path.matches("[1-9]\\d{0,18}")){
            Expense expense = null;
            try{
                long id = Long.parseLong(path);
                expense = ExpenseService.getInstance().getExpenseById(id);
            } catch (NumberFormatException ignored) {}
            return expense;
        }
        return null;
    }
}
