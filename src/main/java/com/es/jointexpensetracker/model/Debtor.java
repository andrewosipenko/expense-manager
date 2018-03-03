package com.es.jointexpensetracker.model;

import java.math.BigDecimal;

public class Debtor {
    private String name;
    private BigDecimal amount;
    private String person;

    public Debtor(String name, BigDecimal amount, String person) {
        this.name = name;
        this.amount = amount;
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
