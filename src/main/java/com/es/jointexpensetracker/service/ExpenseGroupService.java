package com.es.jointexpensetracker.service;

import java.util.UUID;

public class ExpenseGroupService {
    private static volatile ExpenseGroupService expenseGroupService;

    public static ExpenseGroupService getInstance(){
        if(expenseGroupService == null) {
            synchronized (ExpenseGroupService.class){
                if(expenseGroupService == null) {
                    expenseGroupService = new ExpenseGroupService();
                }
            }
        }
        return expenseGroupService;
    }

    public String createNewExpenseGroupId() {
        String newExpenseGroupId = UUID.randomUUID().toString().toLowerCase();
        ExpenseService.getInstance().addExpenseGroup(newExpenseGroupId);
        return newExpenseGroupId;
    }
}
