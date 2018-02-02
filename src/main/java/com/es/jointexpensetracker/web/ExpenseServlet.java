package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class ExpenseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            Long id = getExpenseID(request);
            request.setAttribute("expense", ExpenseService.getInstance().getExpense(id));
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request,response);
        }
        catch (IllegalArgumentException e)
        {
            response.sendError(response.SC_NOT_FOUND,"There is no such page");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            Long id = getExpenseID(request);
            Expense expense = ExpenseService.getInstance().getExpense(id);
            Map<String,Object> expenseData = parseExpenseData(request);
            fillExpenseWith(expense,expenseData);

            HttpSession session = request.getSession(true);
            session.setAttribute("flashMessage","Expense "+
                    "\""+expense.getDescription()+"\""+" was updated successfully");
            response.sendRedirect(request.getContextPath()+"/expenses");
        }
        catch (NumberFormatException e)
        {
            response.sendError(response.SC_NOT_FOUND);
        }
        catch (IllegalArgumentException | DateTimeParseException e )
        {
            HttpSession session = request.getSession(true);
            session.setAttribute("flashMessage", "Update failed. "+ e.getMessage());
            response.sendRedirect(request.getRequestURL().toString());
        }
    }

    protected Map<String,Object> parseExpenseData(HttpServletRequest request)
    {
        String description =  request.getParameter("description");
        BigDecimal amount = new BigDecimal( getAmount(request) );
        String person =  request.getParameter("person");
        Currency currency = getCurrency(request);
        LocalDate date = getExpenseDate(request);

        Map<String,Object> expenseData = new HashMap<>();
        expenseData.put("description",description);
        expenseData.put("amount",amount);
        expenseData.put("person",person);
        expenseData.put("currency",currency);
        expenseData.put("date",date);
        return expenseData;
    }

    protected void fillExpenseWith(Expense expense,Map<String,Object> expenseData)
    {
        String description =  (String) expenseData.get("description");
        BigDecimal amount = (BigDecimal) expenseData.get("amount");
        String person =  (String) expenseData.get("person");
        Currency currency = (Currency) expenseData.get("currency");
        LocalDate date = (LocalDate) expenseData.get("date");

        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setPerson(person);
        expense.setCurrency(currency);
        expense.setDate(date);
    }

    protected Long getExpenseID(HttpServletRequest request) throws NumberFormatException
    {
        return Long.parseLong(request.getPathInfo().substring(1));
    }

    protected LocalDate getExpenseDate(HttpServletRequest request) throws DateTimeParseException
    {
        try
        {
            return LocalDate.parse(request.getParameter("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        catch (DateTimeParseException e)
        {
            throw new DateTimeParseException("Invalid date",e.getParsedString(),e.getErrorIndex());
        }
    }

    protected boolean isValidCurrency(String currency)
    {
        for (Currency availableCurrency : Currency.getAvailableCurrencies())
        {
            if (currency.equals(availableCurrency.getSymbol()))
            {
                return true;
            }
        }
        return false;
    }

    protected Currency getCurrency(HttpServletRequest request) throws IllegalArgumentException
    {
        if ( isValidCurrency( request.getParameter("currency") ) )
        {
            return Currency.getInstance( request.getParameter("currency"));
        }
        else
        {
            throw new IllegalArgumentException("Invalid currency");
        }
    }

    protected Double getAmount(HttpServletRequest request) throws IllegalArgumentException
    {
        String amount = request.getParameter("amount");
        if (amount.length()<1)
        {
            throw new IllegalArgumentException("Amount shouldn't be empty");
        }
        Double amountValue = Double.parseDouble( amount );
        if (amountValue < 0)
        {
            throw new IllegalArgumentException("Amount shouldn't be negative");
        }
        else
        {
            return amountValue;
        }
    }
}
