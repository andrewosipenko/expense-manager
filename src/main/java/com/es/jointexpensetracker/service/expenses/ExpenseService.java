package com.es.jointexpensetracker.service.expenses;

import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ExpenseService {
    private static ExpenseServicePool servicePool = ExpenseServicePool.getInstance();
    Map<Long, Expense> expenses;
    AtomicLong nextId;
    UUID expenseGroup;

    ExpenseService() {}

    public static ExpenseService getInstance(UUID expenseGroup) {
        return servicePool.getService(expenseGroup);
    }

    public static ExpenseService getInstance(String expenseGroupString) throws IllegalArgumentException {
        UUID expenseGroup = UUID.fromString(expenseGroupString);
        return servicePool.getService(expenseGroup);
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
}
