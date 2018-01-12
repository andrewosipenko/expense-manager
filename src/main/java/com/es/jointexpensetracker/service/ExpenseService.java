package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getExpenses();
    Optional<Expense> getExpenseById(long id);
    boolean deleteExpenseById(long id);
    void addNewExpense(Expense newExpense);
}
