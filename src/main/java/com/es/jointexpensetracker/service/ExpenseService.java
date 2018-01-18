package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.model.ExpenseGroup;
import com.es.jointexpensetracker.service.exceptions.ExpenseGroupNotFoundException;

import java.util.Optional;

public interface ExpenseService {
    ExpenseGroup loadExpensesGroupById(String expenseGroupId) throws ExpenseGroupNotFoundException;
    Optional<Expense> getExpenseById(long id, String expenseGroupId) throws ExpenseGroupNotFoundException;
    boolean deleteExpenseById(long id,String expenseGroupId) throws ExpenseGroupNotFoundException;
    void addNewExpense(Expense newExpense) throws ExpenseGroupNotFoundException;
    String createNewExpenseGroup();
}
