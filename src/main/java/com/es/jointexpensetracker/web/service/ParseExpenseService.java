package com.es.jointexpensetracker.web.service;

import com.es.jointexpensetracker.model.Expense;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Currency;

public class ParseExpenseService
{
    public static Expense parseExpenseData(HttpServletRequest request)
    {
        Expense parsedExpense = new Expense();

        String description =  request.getParameter("description");
        parsedExpense.setDescription(description);

        BigDecimal amount = new BigDecimal(getAmount(request));
        parsedExpense.setAmount(amount);

        String person =  request.getParameter("person");
        parsedExpense.setPerson(person);

        Currency currency = getCurrency(request);
        parsedExpense.setCurrency(currency);

        LocalDate date = getExpenseDate(request);
        parsedExpense.setDate(date);

        return parsedExpense;
    }

    public static void copyExpenseData (Expense destinationExpense, Expense sourceExpense)
    {
        destinationExpense.setDescription(sourceExpense.getDescription());
        destinationExpense.setAmount(sourceExpense.getAmount());
        destinationExpense.setPerson(sourceExpense.getPerson());
        destinationExpense.setCurrency(sourceExpense.getCurrency());
        destinationExpense.setDate(sourceExpense.getDate());
    }

    public static LocalDate getExpenseDate(HttpServletRequest request) throws DateTimeParseException
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

    public static boolean isValidCurrency(String currency)
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

    public static Currency getCurrency(HttpServletRequest request) throws IllegalArgumentException
    {
        if ( isValidCurrency(request.getParameter("currency")))
        {
            return Currency.getInstance( request.getParameter("currency"));
        }
        else
        {
            throw new IllegalArgumentException("Invalid currency");
        }
    }

    public static Long getExpenseID(HttpServletRequest request) throws NumberFormatException
    {
        return Long.parseLong(request.getPathInfo().substring(1));
    }

    public static Double getAmount(HttpServletRequest request) throws IllegalArgumentException
    {
        String amount = request.getParameter("amount");
        if (amount.length() < 1)
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
