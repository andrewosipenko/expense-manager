package com.es.jointexpensetracker.service;


public interface ExpenseGroupService {
    ExpenseService getServiceByGroup(String expenseGroup);
    String createNewGroup();
}
