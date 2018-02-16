package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.model.PersonTotalChartItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public final class StatisticsService {
    private static final StatisticsService instance = new StatisticsService();
    private final ChartColorService colorService = ChartColorService.getInstance();

    private StatisticsService() {}

    public static StatisticsService getInstance() {
        return instance;
    }

    public List<PersonTotalChartItem> getChartData(Collection<Expense> expenses) {
        if (expenses.isEmpty())
            return Collections.emptyList();
        List<PersonTotalChartItem> items = expenses.stream()
                .map(Expense::getHelper)
                .collect(Collectors.toMap(Expense.ConsistencyHelper::getPerson, Expense.ConsistencyHelper::getAmount, BigDecimal::add))
                .entrySet().stream()
                .map(entry -> new PersonTotalChartItem(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        int number = items.size();
        List<String> colors = colorService.getColorsAsHexStrings(number);
        List<String> highlightedColors = colorService.getHighlightedColorsAsHexStrings(number);
        for (int i = 0; i < number; i++) {
            items.get(i).setHexColor(colors.get(i));
            items.get(i).setHexHighlightedColor(highlightedColors.get(i));
        }
        return items;
    }

    public List<Debt> getDebts(List<PersonTotalChartItem> totalChartItems) {
        if (totalChartItems.isEmpty())
            return Collections.emptyList();
        totalChartItems = totalChartItems.stream()
                .sorted(Comparator.comparing(PersonTotalChartItem::getTotalAmount))
                .collect(Collectors.toList());
        String[] names = totalChartItems.stream()
                .map(PersonTotalChartItem::getName)
                .toArray(String[]::new);
        BigDecimal[] amounts = totalChartItems.stream()
                .map(PersonTotalChartItem::getTotalAmount)
                .toArray(BigDecimal[]::new);
        BigDecimal average = Arrays.stream(amounts)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(amounts.length), 2, RoundingMode.HALF_UP);

        int i = 0, j = amounts.length-1;
        List<Debt> debts = new ArrayList<>();
        while (i < j) {
            BigDecimal oneDiff = average.subtract(amounts[i]), twoDiff = amounts[j].subtract(average);
            if (oneDiff.compareTo(twoDiff) < 0) {
                debts.add(new Debt(names[j], names[i], oneDiff));
                amounts[j] = amounts[j].subtract(oneDiff);
                i++;
            } else if (oneDiff.compareTo(twoDiff) > 0) {
                debts.add(new Debt(names[j], names[i], twoDiff));
                amounts[i] = amounts[i].add(twoDiff);
                j--;
            } else {
                debts.add(new Debt(names[j], names[i], oneDiff));
                i++;
                j--;
            }
            if (amounts[j].subtract(average).abs().compareTo(BigDecimal.valueOf(0.01)) < 0)
                break;
        }
        return debts;
    }
}
