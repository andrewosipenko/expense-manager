package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.model.PersonTotalChartItem;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.StatisticsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Currency;
import java.util.List;

public class StatisticsServlet extends HttpServlet {
    private StatisticsService statisticsService;

    @Override
    public void init() throws ServletException {
        statisticsService = StatisticsService.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ExpenseService expenseService = (ExpenseService) request.getAttribute("expenseService");
        if (expenseService == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Collection<Expense> expenses = expenseService.getExpenses();
        List<PersonTotalChartItem> chartData = statisticsService.getChartData(expenses);
        List<Debt> debts = statisticsService.getDebts(chartData);
        request.setAttribute("chartData", chartData);
        request.setAttribute("debts", debts);
        request.setAttribute("debtsCurrency", Currency.getInstance("USD"));
        request.getRequestDispatcher("/WEB-INF/pages/statistics.jsp").forward(request, response);
    }
}
