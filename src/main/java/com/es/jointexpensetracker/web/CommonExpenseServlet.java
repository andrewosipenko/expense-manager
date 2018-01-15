package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;

public abstract class CommonExpenseServlet extends HttpServlet {

    protected boolean updateExpenseByRequest(Expense editExpense, HttpServletRequest request){
        try {
            String description = request.getParameter("description");
            if(description == null){
                return false;
            }

            BigDecimal amount;

            String amountStr = request.getParameter("amount");
            if(amountStr == null){
                return false;
            }
            amount = new BigDecimal(amountStr);


            Currency currency;
            String currencyStr = request.getParameter("currency");
            if(currencyStr == null){
                return false;
            }
            currencyStr = currencyStr.toUpperCase();
            currency = Currency.getInstance(currencyStr);

            String person = request.getParameter("person");
            if(description == null){
                return false;
            }

            LocalDate date;
            String dateStr = request.getParameter("date");
            if(dateStr == null){
                return false;
            }
            date = LocalDate.parse(dateStr);

            editExpense.setDescription(description);
            editExpense.setAmount(amount);
            editExpense.setCurrency(currency);
            editExpense.setPerson(person);
            editExpense.setDate(date);
            return true;

        }catch (DateTimeParseException | IllegalArgumentException e){
            return false;
        }
    }
}
