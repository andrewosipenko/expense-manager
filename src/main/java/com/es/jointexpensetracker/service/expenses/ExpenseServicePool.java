package com.es.jointexpensetracker.service.expenses;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

final class ExpenseServicePool {
    private static final ExpenseServicePool instance = new ExpenseServicePool();
    private Map<UUID, ExpenseService> serviceMap = new HashMap<>();
    private final Object lock = new Object();

    private ExpenseServicePool() {}

    static ExpenseServicePool getInstance() { return instance; }

    ExpenseService getService(UUID expenseGroup) {
        return getService(expenseGroup, true);
    }

    ExpenseService getService(UUID expenseGroup, boolean addIfNotPresent) {
        if (!addIfNotPresent || serviceMap.containsKey(expenseGroup))
            return serviceMap.get(expenseGroup);
        synchronized (lock) {
            if (!serviceMap.containsKey(expenseGroup))
                addService(expenseGroup);
            return serviceMap.get(expenseGroup);
        }
    }

    private void addService(UUID expenseGroup) {
        // whatever logic of acquiring an ExpenseService object by its expenseGroup uuid
        serviceMap.put(expenseGroup, new DemoDataExpenseService(expenseGroup));
    }
}
