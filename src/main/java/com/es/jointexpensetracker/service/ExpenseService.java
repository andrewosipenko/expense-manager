package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ExpenseService {
    Map<Long, Expense> expenses;
    AtomicLong nextId;
    String expenseGroup;

    ExpenseService() {}

    public static ExpenseService getInstance() {
        return ExpenseServiceHolder.instance;
    }

    public Collection<Expense> getExpenses() {
        return Collections.unmodifiableCollection(expenses.values());
    }

    public Expense getExpenseById(long id) {
        return expenses.get(id);
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense.getId());
    }

    public Expense addExpense(String person, String description, BigDecimal amount, Currency currency, LocalDate date) {
        long id = nextId.getAndIncrement();
        Expense expense = new Expense(id, description, amount, currency, person, date, expenseGroup);
        expenses.put(id, expense);
        return expense;
    }

    private static class ExpenseServiceHolder {
        private static final ExpenseService instance = new DemoDataExpenseService();
    }
}
