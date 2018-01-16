package com.es.jointexpensetracker.web.servlets;


import com.es.jointexpensetracker.model.Debt;
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

public class StatisticsServlet extends CommonExpenseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExpenseService expenseService = loadExpenseService(request);

        Map<String,BigDecimal> mapExpenses = expenseService.getMapExpenses();
        List<Debt> debtList = expenseService.getDebts();

        request.setAttribute(
                "persons" , mapExpenses.keySet()
        );
        request.setAttribute(
                "amounts" , mapExpenses.values()
        );
        request.setAttribute(
                "debts" , debtList
        );

        request.getRequestDispatcher("/WEB-INF/pages/statistics.jsp").forward(request, response);
    }
}
