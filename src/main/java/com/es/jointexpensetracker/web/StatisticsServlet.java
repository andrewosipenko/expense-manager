package com.es.jointexpensetracker.web;


import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.impl.HardcodeExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsServlet extends HttpServlet {

    private ExpenseService expenseService;

    @Override
    public void init() throws ServletException {
        super.init();
        expenseService = HardcodeExpenseService.getInstance();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,BigDecimal> mapExpenses = getMapExpenses();

        request.setAttribute(
                "persons" , mapExpenses.keySet()
        );

        request.setAttribute(
                "amounts" , mapExpenses.values()
        );

        request.getRequestDispatcher("/WEB-INF/pages/statistics.jsp").forward(request, response);
    }

    private Map<String,BigDecimal> getMapExpenses(){
        List<Expense> expenseList = expenseService.getExpenses();
        Map<String,BigDecimal> mapExpenses = expenseList.stream().collect(
                Collectors.toMap(Expense::getPerson, Expense::getAmount, BigDecimal::add)
        );
        return mapExpenses;
    }
}
