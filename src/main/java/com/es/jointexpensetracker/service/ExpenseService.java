package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.exception.DataNotFoundException;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.model.ExpenseKey;

import java.util.List;

public interface ExpenseService {

    String createExpenseGroup();

    List<Expense> getExpensesByGroup(String expenseGroup);

    Expense loadExpenseByKey(ExpenseKey key) throws DataNotFoundException;

    Expense addExpense(String expenseGroup);

    void removeExpense(Expense expense);

    void save();
}
