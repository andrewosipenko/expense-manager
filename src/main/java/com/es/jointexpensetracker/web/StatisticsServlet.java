package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.DebtService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class StatisticsServlet extends HttpServlet {
    private DebtService debtService;

    @Override
    public void init() throws ServletException {
        debtService = DebtService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, BigDecimal> expenseMap = debtService.getTotalExpenses();
        request.setAttribute("people" , expenseMap.keySet());
        request.setAttribute("amounts", expenseMap.values());
        request.setAttribute("debtors", debtService.getDebtors());
        request.getRequestDispatcher("WEB-INF/pages/statistics.jsp").forward(request, response);
    }
}
