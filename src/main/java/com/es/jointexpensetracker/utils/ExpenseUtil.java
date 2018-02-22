package com.es.jointexpensetracker.utils;

import com.es.jointexpensetracker.constants.Constants;
import com.es.jointexpensetracker.exception.ExpenseGroupNotFoundException;
import com.es.jointexpensetracker.exception.InvalidPathException;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.DebtService;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class ExpenseUtil {
    public static Optional<Expense> getValidExpense(HttpServletRequest request) throws InvalidPathException, IOException {
        String groupId = (String)request.getAttribute(Constants.EXPENSE_GROUP_ID);
        Expense expense = null;
        if(!isEmptyInput(request,"amount") &&
                !isEmptyInput(request,"person")) {
            expense = new Expense(
                    (getId(request)),
                    request.getParameter("description"),
                    BigDecimal.valueOf(Long.valueOf(request.getParameter("amount"))),
                    Currency.getInstance(request.getParameter("currency")),
                    request.getParameter("person").trim(),
                    LocalDate.parse(request.getParameter("date")),
                    groupId
            );
        }
        return Optional.ofNullable(expense);
    }

    private static boolean isEmptyInput(HttpServletRequest request, String requestParam) {
        return  request.getParameter(requestParam).trim().equals("");
    }

    public static Long getId(HttpServletRequest request) throws InvalidPathException, IOException {
        String groupId = (String)request.getAttribute(Constants.EXPENSE_GROUP_ID);
        String pathInfo = request.getPathInfo();
        if(pathInfo.substring(Constants.SKIP_SLASH).matches("[1-9]+[0-9]*")) {
            return Long.parseLong(pathInfo.substring(Constants.SKIP_SLASH));
        }
        else if (pathInfo.substring(Constants.SKIP_SLASH).equals("add")) {
           return  Collections.max(ExpenseService.getInstance(groupId).getCustomGroupExpenses(),
                   Comparator.comparing(Expense::getId)).getId() + 1;
        } else throw new InvalidPathException();
    }

    public static ExpenseService getExpenseServiceByRequestPath(HttpServletRequest request) throws ExpenseGroupNotFoundException{
        String expenseGroupId = (String) request.getAttribute(Constants.EXPENSE_GROUP_ID);
        if(expenseGroupId == null)
            throw  new ExpenseGroupNotFoundException();
        return  ExpenseService.getInstance(expenseGroupId);
    }

    public static DebtService getDebtServiceByRequestPath(HttpServletRequest request) throws ExpenseGroupNotFoundException {
        String expenseGroupId = (String) request.getAttribute(Constants.EXPENSE_GROUP_ID);
        System.out.println("*****" + expenseGroupId);
        if(expenseGroupId == null )
            throw  new ExpenseGroupNotFoundException();
        return  DebtService.getInstance(expenseGroupId);
    }

    public static String getExpenseGroupIdFromRequest(HttpServletRequest request) {
        return (String)request.getAttribute(Constants.EXPENSE_GROUP_ID);
    }
}
