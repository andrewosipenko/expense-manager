package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;

public class ExpenseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo().substring(1);
        if (path.matches("[1-9]\\d{0,18}"))
            processShowExpenseInfo(request, response);
        else
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo().substring(1);
        if (path.matches("[1-9]\\d{0,18}"))
            processUpdateExpense(request, response);
        else
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void processShowExpenseInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = request.getPathInfo().substring(1);
        long id;
        try {
            id = Long.parseLong(path);
        } catch (NumberFormatException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ExpenseService service = ExpenseService.getInstance();
        Expense expense = service.getExpenseById(id);
        if (expense == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        request.setAttribute("expense", expense);
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
    }

    private void processUpdateExpense(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = request.getPathInfo().substring(1);
        long id;
        try {
            id = Long.parseLong(path);
        } catch (NumberFormatException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ExpenseService service = ExpenseService.getInstance();
        Expense expense = service.getExpenseById(id);
        if (expense == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String person = request.getParameter("person"),
                description = request.getParameter("description"),
                textAmount = request.getParameter("amount"),
                textCurrency = request.getParameter("currency"),
                textDate = request.getParameter("date");
        if (person==null || textAmount==null || textCurrency==null || description==null || textDate==null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        BigDecimal amount;
        Currency currency;
        LocalDate date;
        try {
            amount = new BigDecimal(textAmount);
            currency = Currency.getInstance(textCurrency);
            date = LocalDate.parse(textDate);
        } catch (IllegalArgumentException | DateTimeParseException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        expense.setAmount(amount);
        expense.setCurrency(currency);
        expense.setDate(date);
        expense.setDescription(description);
        expense.setPerson(person);

        response.sendRedirect(request.getContextPath() + "/expenses");
    }
}
