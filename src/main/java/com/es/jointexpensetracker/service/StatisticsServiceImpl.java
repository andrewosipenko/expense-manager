package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsServiceImpl implements StatisticsService{

    public Map<String, BigDecimal> getChart(List<Expense> expenses){
        return expenses.stream()
                .collect(Collectors.toMap(
                        (a) -> String.format("'%s'", a.getPerson()),
                        Expense::getAmount,
                        BigDecimal::add
                ));
    }

}
