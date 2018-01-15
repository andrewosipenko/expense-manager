package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getExpenses();
    Optional<Expense> getExpenseById(long id);
    boolean deleteExpenseById(long id);
    void addNewExpense(Expense newExpense);
    List<Debt> getDebts();
    Map<String,BigDecimal> getMapExpenses();
}
