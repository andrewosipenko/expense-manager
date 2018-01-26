package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Currency;

public class ExpenseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            int id = getExpenseID(request);
            if(id<1 || id>ExpenseService.getInstance().getExpenses().size())
            {
                throw new NumberFormatException();
            }
            request.setAttribute("expense", ExpenseService.getInstance().getExpense(id-1));
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request,response);
        }
        catch (NumberFormatException e)
        {
            request.setAttribute("exception","There is no such page :c");
            request.getRequestDispatcher("/WEB-INF/pages/page404.jsp").forward(request,response);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            int id = getExpenseID(request);

            String description =  request.getParameter("description");

            Integer amountValue = Integer.parseInt( request.getParameter("amount"));
            BigDecimal amount = new BigDecimal(amountValue);

            String person =  request.getParameter("person");

            Currency currency;
            if ( isValidCurrency( request.getParameter("currency") ) )
            {
                currency = Currency.getInstance( request.getParameter("currency"));
            }
            else
            {
                throw new IllegalArgumentException("Invalid currency");
            }

            LocalDate date = getExpenseDate(request);

            Expense expense = ExpenseService.getInstance().getExpense(id-1);
            expense.setDescription(description);
            expense.setAmount(amount);
            expense.setPerson(person);
            expense.setCurrency(currency);
            expense.setDate(date);

            response.sendRedirect(request.getContextPath()+"/expenses");
        }
        catch (IllegalArgumentException |DateTimeParseException e )
        {
            request.setAttribute("exception",e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/page404.jsp").forward(request,response);
            return;
        }
    }

    private int getExpenseID(HttpServletRequest request) throws NumberFormatException
    {
        return Integer.parseInt(request.getPathInfo().substring(1));
    }

    private LocalDate getExpenseDate(HttpServletRequest request) throws DateTimeParseException
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

    private boolean isValidCurrency(String currency)
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


}
