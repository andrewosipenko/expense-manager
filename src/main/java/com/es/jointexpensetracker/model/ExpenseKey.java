package com.es.jointexpensetracker.model;

import java.util.Objects;

public class ExpenseKey {
    private Long id;

    private String expenseGroup;

    public ExpenseKey(Long id, String expenseGroup){
        this.id = id;
        this.expenseGroup = expenseGroup;
    }

    public Long getId() {
        return id;
    }

    public String getExpenseGroup() {
        return expenseGroup;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, expenseGroup);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(!(obj instanceof ExpenseKey)) return false;
        ExpenseKey other = (ExpenseKey) obj;
        return this.getExpenseGroup().equals(other.getExpenseGroup()) &&
                this.getId().equals(other.getId());
    }
}
