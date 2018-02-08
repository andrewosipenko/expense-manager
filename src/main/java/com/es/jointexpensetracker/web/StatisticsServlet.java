package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ChartColorService;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsServlet extends HttpServlet{
    private ExpenseService expenseService;
    private ChartColorService colorService;

    @Override
    public void init() throws ServletException {
        expenseService = ExpenseService.getInstance();
        colorService = ChartColorService.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Collection<Expense> expenses = expenseService.getExpenses();
        Set<Map.Entry<String, BigDecimal>> expenseHelpers = expenses.stream().map(Expense::getHelper).collect(Collectors.toMap(
                Expense.ConsistencyHelper::getPerson,
                Expense.ConsistencyHelper::getAmount,
                BigDecimal::add,
                HashMap::new
        )).entrySet();
        List<String> names = expenseHelpers.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        List<BigDecimal> amounts = expenseHelpers.stream().map(Map.Entry::getValue).collect(Collectors.toList());
        List<String> colors = colorService.getColorsAsHexStrings(expenseHelpers.size());
        List<String> highlightedColors = colorService.getHighlightedColorsAsHexStrings(expenseHelpers.size());

        request.setAttribute("names", names);
        request.setAttribute("amounts", amounts);
        request.setAttribute("colors", colors);
        request.setAttribute("highlightedColors", highlightedColors);

        request.getRequestDispatcher("/WEB-INF/pages/statistics.jsp").forward(request, response);
    }
}
