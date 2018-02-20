package com.es.jointexpensetracker.web.servlet;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.web.service.DebtsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Map<String, BigDecimal> paymentsMap = ExpenseService.getInstance().getExpenses().stream().collect(Collectors.toMap(
                Expense::getPerson, Expense::getAmount, BigDecimal::add));
        req.setAttribute("expenses", paymentsMap);
        req.setAttribute("debts", DebtsService.getDebtsList(paymentsMap));
        req.getRequestDispatcher("/WEB-INF/pages/statistics.jsp").forward(req, resp);
    }
}