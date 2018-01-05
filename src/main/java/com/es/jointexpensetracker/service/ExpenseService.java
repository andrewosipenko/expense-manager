package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.exception.DataNotFoundException;
import com.es.jointexpensetracker.model.Expense;

import java.util.List;

public interface ExpenseService {

    List<Expense> getExpenses();

    Expense loadExpenseById(long id) throws DataNotFoundException;

    void save();
}
