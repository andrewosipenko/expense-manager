package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.DebtService;
import com.es.jointexpensetracker.utils.ExpenseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StatisticsServlet extends HttpServlet {

    private DebtService debtService;


    @Override
    public void init() throws ServletException {
        debtService = DebtService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, BigDecimal> expenseMap = debtService.totalExpenses();
        Set<String> names = expenseMap.keySet();
        request.setAttribute("people" , names );
        request.setAttribute("amounts", expenseMap.values());
        request.getRequestDispatcher("WEB-INF/pages/statistics.jsp").forward(request, response);
    }
}
