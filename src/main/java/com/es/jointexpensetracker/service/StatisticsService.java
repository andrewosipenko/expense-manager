package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface StatisticsService {

    Map<String, BigDecimal> getChartInfo(List<Expense> expenses);

}
