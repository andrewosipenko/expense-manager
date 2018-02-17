package com.es.jointexpensetracker.service.expenses;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class BlankExpenseService extends ExpenseService {
    BlankExpenseService(UUID expenseGroup) {
        this.expenseGroup = expenseGroup;
        expenses = new ConcurrentHashMap<>();
        nextId = new AtomicLong(1L);
    }
}
