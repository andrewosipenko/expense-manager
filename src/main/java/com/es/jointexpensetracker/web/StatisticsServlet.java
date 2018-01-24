package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.ExpenseServiceSingleton;
import com.es.jointexpensetracker.service.StatisticsService;
import com.es.jointexpensetracker.service.StatisticsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class StatisticsServlet extends HttpServlet {

    private final static String STATISTICS_JSP_PATH = "/WEB-INF/pages/statistics.jsp";
    private StatisticsService statisticsService;

    @Override
    public void init() throws ServletException {
        this.statisticsService = new StatisticsServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final ExpenseService expenseService = ExpenseServiceSingleton.getInstance();

        Map<String, BigDecimal> chart = statisticsService.getChart(expenseService.getExpenses());

        request.setAttribute("chartNames", chart.keySet());
        request.setAttribute("chartData", chart.values());

        request.getRequestDispatcher(STATISTICS_JSP_PATH).forward(request, response);
    }
}
