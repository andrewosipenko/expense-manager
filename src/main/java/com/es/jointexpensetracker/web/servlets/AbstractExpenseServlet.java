package com.es.jointexpensetracker.web.servlets;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.web.Constants;
import com.es.jointexpensetracker.web.exceptions.HttpNotFoundException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;

public abstract class AbstractExpenseServlet extends HttpServlet {

    protected String loadExpenseGroupId(HttpServletRequest request){
        String expenseGroupId = (String)request.getAttribute(Constants.EXPENSE_GROUP_ATTRIBUTE_NAME);
        if(expenseGroupId == null){
            throw new HttpNotFoundException();
        }
        return expenseGroupId;
    }

    protected boolean updateExpenseFromRequest(Expense expense, HttpServletRequest request){
        try {
            String description = request.getParameter("description");
            if(description == null){
                return false;
            }

            String amountStr = request.getParameter("amount");
            if(amountStr == null){
                return false;
            }
            BigDecimal amount = new BigDecimal(amountStr);


            String currencyStr = request.getParameter("currency");
            if(currencyStr == null){
                return false;
            }
            currencyStr = currencyStr.toUpperCase();
            Currency currency = Currency.getInstance(currencyStr);

            String person = request.getParameter("person");
            if(person == null){
                return false;
            }

            String dateStr = request.getParameter("date");
            if(dateStr == null){
                return false;
            }
            LocalDate date = LocalDate.parse(dateStr);

            expense.setDescription(description);
            expense.setAmount(amount);
            expense.setCurrency(currency);
            expense.setPerson(person);
            expense.setDate(date);
            return true;

        }catch (DateTimeParseException | IllegalArgumentException e){
            return false;
        }
    }
}
