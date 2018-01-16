package com.es.jointexpensetracker.web.servlets;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseGroupService;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.impl.HardcodeExpenseGroupService;
import com.es.jointexpensetracker.web.exceptions.HttpNotFoundException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Currency;

public abstract class CommonExpenseServlet extends HttpServlet {

    protected ExpenseGroupService expenseGroupService = HardcodeExpenseGroupService.getInstanse();

    protected ExpenseService loadExpenseService(HttpServletRequest request){
        String expenseGroup = (String)request.getAttribute("expenseGroup");
        ExpenseService expenseService = expenseGroupService.getServiceByGroup(expenseGroup);
        if(expenseService == null){
            throw new HttpNotFoundException();
        }
        return expenseService;
    }

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
