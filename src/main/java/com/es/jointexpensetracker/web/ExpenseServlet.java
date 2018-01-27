package com.es.jointexpensetracker.web;


import com.es.jointexpensetracker.constants.Constants;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;


public class ExpenseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final int PATH_MISMATCH = 0;

        Long id = getID(request);
        if(id != PATH_MISMATCH) {
            request.setAttribute("expense", ExpenseService.getInstance().getOne(id));
            request.setAttribute("currencies", Currency.getAvailableCurrencies());
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
        }
        else {
            throw new IllegalArgumentException("Request path mismatch");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long id = getID(request);
        if(id != Constants.PATH_MISMATCH) {
            Expense expense = getValidExpense(request);
            if(expense != null) {

                setExpenseUpdate(expense);
                response.sendRedirect(request.getContextPath()+"/expenses");
            } else {
                request.setAttribute("expense", ExpenseService.getInstance().getOne(id));
                request.setAttribute("currencies", Currency.getAvailableCurrencies());
                request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
            }

        } else {
            throw new IllegalArgumentException("Request path mismatch");
        }

    }


    private Long getID(HttpServletRequest request) {

        String pathInfo = request.getPathInfo();
        if(pathInfo.substring(Constants.SKIP_SLASH).matches("[1-9]+[0-9]*")) {
            return Long.parseLong(pathInfo.substring(Constants.SKIP_SLASH));
        }
        else {
            return ((long) Constants.PATH_MISMATCH);
        }
    }

    private void setExpenseUpdate(Expense expense) {

        ExpenseService.getInstance().getOne(expense.getId()).setDescription(expense.getDescription());
        ExpenseService.getInstance().getOne(expense.getId()).setAmount(expense.getAmount());
        ExpenseService.getInstance().getOne(expense.getId()).setCurrency(expense.getCurrency());
        ExpenseService.getInstance().getOne(expense.getId()).setPerson(expense.getPerson());
        ExpenseService.getInstance().getOne(expense.getId()).setDate(expense.getDate());
        ExpenseService.getInstance().getOne(expense.getId()).setExpenseGroup(expense.getExpenseGroup());
    }

    private Expense getValidExpense(HttpServletRequest request) {

        Expense expense = null;
        boolean emptyInput = false;

        Map<String,String> params = new HashMap<>();
        params.put("description",request.getParameter("description"));
        params.put("amount",request.getParameter("amount"));
        params.put("person",request.getParameter("person"));
        params.put("expenseGroup",request.getParameter("expenseGroup"));

        for(Map.Entry<String,String> entry : params.entrySet()) {
            if(entry.getValue().trim().equals("")) {
                emptyInput = true;
                break;
            }
        }

        if(!emptyInput) {
             expense = new Expense(
                    (getID(request)),
                    request.getParameter("description"),
                    BigDecimal.valueOf(Long.valueOf(request.getParameter("amount"))),
                    Currency.getInstance(request.getParameter("currency")),
                    request.getParameter("person"),
                    LocalDate.parse(request.getParameter("date")),
                    request.getParameter("expenseGroup")
            );
        }

        return expense;
    }
}
