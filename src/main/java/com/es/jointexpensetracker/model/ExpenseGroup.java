package com.es.jointexpensetracker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseGroup {
    private List<Expense> expenseList;
    private String groupId;

    public ExpenseGroup(String groupId) {
        List<Expense> expenseList = new ArrayList<>();
        this.expenseList = Collections.synchronizedList(expenseList);
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }
}
