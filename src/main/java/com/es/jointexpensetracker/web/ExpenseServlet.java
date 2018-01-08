package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.impl.HardcodeExpenseService;
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

public class ExpenseServlet extends HttpServlet {

    private ExpenseService expenseService;

    @Override
    public void init() throws ServletException {
        super.init();
        expenseService = HardcodeExpenseService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int idExpense = getIdExpenseFromRequest(request);

            Expense expense = expenseService
                    .getExpenseById(idExpense)
                    .orElse(null);

            if(expense == null){
                request.setAttribute(
                        "message", "Expense not found =("
                );
            }
            else {
                request.setAttribute(
                        "expense", expense
                );
            }
        }catch(NumberFormatException e){
            request.setAttribute(
                    "message", "Expense not found =("
            );
        }
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int idExpense = getIdExpenseFromRequest(request);

            Expense expense = expenseService
                    .getExpenseById(idExpense)
                    .orElse(null);

            if(expense != null){
                if(changeExpenseByRequest(expense,request)){
                    request.getSession().setAttribute("infoMessage","Expense \""+expense.getDescription()+"\" was update successfully");
                    response.sendRedirect(request.getContextPath() + "/expenses");
                    return;
                }
                else{
                    request.setAttribute(
                            "message", "Please, check input data"
                    );
                    request.setAttribute(
                            "expense", expense
                    );
                }
            }
            else {
                request.setAttribute(
                        "message", "Expense not found =("
                );
            }
        }catch(NumberFormatException e){
            request.setAttribute(
                    "message", "Expense not found =("
            );
        }
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
    }

    private int getIdExpenseFromRequest(HttpServletRequest request){
        final int SKIP_SLASH_VALUE = 1;

        String pathInfo = request.getPathInfo();
        if(pathInfo == null){
            throw new NullPointerException();
        }

        String idExpense = pathInfo.substring(SKIP_SLASH_VALUE);

        return Integer.parseUnsignedInt(idExpense);
    }

    private boolean changeExpenseByRequest(Expense editExpense,HttpServletRequest request){
        String description = request.getParameter("description");
        if(!Validator.validateString(description)){
            return false;
        }

        BigDecimal amount;
        try {
            String amountStr = request.getParameter("amount");
            if(amountStr == null){
                return false;
            }
            amount = new BigDecimal(amountStr);
        }catch (NumberFormatException e){
            return false;
        }

        Currency currency;
        try{
            String currencyStr = request.getParameter("currency");
            if(currencyStr == null){
                return false;
            }
            currencyStr = currencyStr.toUpperCase();
            currency = Currency.getInstance(currencyStr);
        }catch(IllegalArgumentException e){
            return false;
        }

        String person = request.getParameter("person");
        if(!Validator.validateString(person)){
            return false;
        }

        LocalDate date;

        try{
            String dateStr = request.getParameter("date");
            if(dateStr == null){
                return false;
            }
            date = LocalDate.parse(dateStr);
        }catch (DateTimeParseException e){
            return false;
        }

        editExpense.setDescription(description);
        editExpense.setAmount(amount);
        editExpense.setCurrency(currency);
        editExpense.setPerson(person);
        editExpense.setDate(date);
        return true;
    }
}
