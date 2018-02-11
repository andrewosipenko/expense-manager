package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ChartColorService;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
        Set<Map.Entry<String, BigDecimal>> entries = expenses.stream().map(Expense::getHelper).collect(Collectors.toMap(
                Expense.ConsistencyHelper::getPerson,
                Expense.ConsistencyHelper::getAmount,
                BigDecimal::add,
                HashMap::new
        )).entrySet();

        setChartData(entries, request);
        setDebtsData(entries, request);
        request.getRequestDispatcher("/WEB-INF/pages/statistics.jsp").forward(request, response);
    }

    private void setChartData(Set<Map.Entry<String, BigDecimal>> data, HttpServletRequest request){
        List<String> names = data.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        List<BigDecimal> amounts = data.stream().map(Map.Entry::getValue).collect(Collectors.toList());
        List<String> colors = colorService.getColorsAsHexStrings(names.size());
        List<String> highlightedColors = colorService.getHighlightedColorsAsHexStrings(names.size());

        request.setAttribute("names", names);
        request.setAttribute("amounts", amounts);
        request.setAttribute("colors", colors);
        request.setAttribute("highlightedColors", highlightedColors);
    }

    private void setDebtsData(Set<Map.Entry<String, BigDecimal>> data, HttpServletRequest request){
        List<Map.Entry<String, BigDecimal>> entries = data.stream().sorted(Comparator.comparing(Map.Entry::getValue)).collect(Collectors.toList());
        String[] names = entries.stream().map(Map.Entry::getKey).toArray(String[]::new);
        BigDecimal[] amounts = entries.stream().map(Map.Entry::getValue).toArray(BigDecimal[]::new);
        BigDecimal average = Arrays.stream(amounts).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(amounts.length), 2, RoundingMode.HALF_UP);

        int i = 0, j = amounts.length-1;
        List<Debt> debts = new ArrayList<>();
        while (i < j){
            BigDecimal oneDiff = average.subtract(amounts[i]), twoDiff = amounts[j].subtract(average);
            if (oneDiff.compareTo(twoDiff) < 0){
                debts.add(new Debt(names[j], names[i], oneDiff));
                amounts[j] = amounts[j].subtract(oneDiff);
                i++;
            } else if (oneDiff.compareTo(twoDiff) > 0){
                debts.add(new Debt(names[j], names[i], twoDiff));
                amounts[i] = amounts[i].add(twoDiff);
                j--;
            } else {
                debts.add(new Debt(names[j], names[i], oneDiff));
                i++;
                j--;
            }
        }
        request.setAttribute("debts", debts);
        request.setAttribute("debtsCurrency", Currency.getInstance("USD"));
    }
}
