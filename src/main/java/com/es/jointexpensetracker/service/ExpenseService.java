package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.exception.DataNotFoundException;
import com.es.jointexpensetracker.model.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getExpensesByGroup(String expenseGroup);

    Expense loadExpenseByKey(Expense.ExpenseKey key) throws DataNotFoundException;

    Expense createExpense();

    void removeExpense(Expense expense);

    void save();
}
