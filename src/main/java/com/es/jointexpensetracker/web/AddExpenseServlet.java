package com.es.jointexpensetracker.web;


import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.impl.ExpenseServiceImpl;
import com.es.jointexpensetracker.web.util.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;

public class AddExpenseServlet extends HttpServlet {

    private ExpenseService expenseService;

    @Override
    public void init() throws ServletException {
        super.init();
        expenseService = ExpenseServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(
                "showAddForm" , true
        );
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            Expense expense = createExpenseFromRequest(request);

            if (expense != null) {
                expenseService.addNewExpense(expense);
                request.getSession().setAttribute("infoMessage", "Expense \"" + expense.getDescription() + "\" was created successfully");
                response.sendRedirect(request.getContextPath() + "/expenses");
                return;
            } else {
                request.setAttribute(
                        "message", "Please, check input data"
                );
            }
        }catch(NullPointerException | NumberFormatException e){
            request.setAttribute(
                    "message", "Unknown error"
            );
        }
        request.setAttribute(
                "showAddForm" , true
        );
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
    }

    private Expense createExpenseFromRequest(HttpServletRequest request){
        String description = request.getParameter("description");
        if(!Validator.validateString(description)){
            return null;
        }

        BigDecimal amount;
        try {
            String amountStr = request.getParameter("amount");
            if(amountStr == null){
                return null;
            }
            amount = new BigDecimal(amountStr);
        }catch (NumberFormatException e){
            return null;
        }

        Currency currency;
        try{
            String currencyStr = request.getParameter("currency");
            if(currencyStr == null){
                return null;
            }
            currencyStr = currencyStr.toUpperCase();
            currency = Currency.getInstance(currencyStr);
        }catch(IllegalArgumentException e){
            return null;
        }

        String person = request.getParameter("person");
        if(!Validator.validateString(person)){
            return null;
        }

        LocalDate date;

        try{
            String dateStr = request.getParameter("date");
            if(dateStr == null){
                return null;
            }
            date = LocalDate.parse(dateStr);
        }catch (DateTimeParseException e){
            return null;
        }

        return new Expense(description,amount,currency,person,date);
    }

}
