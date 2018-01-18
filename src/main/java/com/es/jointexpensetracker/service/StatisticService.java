package com.es.jointexpensetracker.service;


import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.service.exceptions.ExpenseGroupNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface StatisticService {

    List<Debt> loadDebtsByExpenseGroupId(String expenseGroupId) throws ExpenseGroupNotFoundException;
    /**
     * @param expenseGroupId
     * @return A Map in which the key - a person name; the value - a sum of expenses ot the person
     */
    Map<String,BigDecimal> loadExpensesMapByExpenseGroupId(String expenseGroupId) throws ExpenseGroupNotFoundException;
}
