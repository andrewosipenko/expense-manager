package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getExpenses();
    List<Debt> getDebts();
    Optional<Expense> getExpenseById(long id);
    Map<String,BigDecimal> getMapExpenses();
    String getExpenseGroup();
    boolean deleteExpenseById(long id);
    void addNewExpense(Expense newExpense);
}
