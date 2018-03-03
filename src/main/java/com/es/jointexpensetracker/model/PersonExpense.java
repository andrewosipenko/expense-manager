package com.es.jointexpensetracker.model;

import java.math.BigDecimal;

public class PersonExpense {
    private String person;
    private BigDecimal amount;

    public PersonExpense(String person, BigDecimal amount) {
        this.person = person;
        this.amount = amount;
    }

    public String getPerson() {
        return person;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
