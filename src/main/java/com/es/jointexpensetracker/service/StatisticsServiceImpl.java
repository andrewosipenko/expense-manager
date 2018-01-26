package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsServiceImpl implements StatisticsService{

    @Override
    public Map<String, BigDecimal> getChartInfo(List<Expense> expenses){
        return expenses.stream()
                .collect(Collectors.toMap(
                        (a) -> String.format("'%s'", a.getPerson()),
                        Expense::getAmount,
                        BigDecimal::add,
                        LinkedHashMap::new
                ));
    }

}
