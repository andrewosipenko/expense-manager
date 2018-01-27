package com.es.jointexpensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class Expense {
    private final Long id;
    private String description;
    private BigDecimal amount;
    private Currency currency;
    private String person;
    private LocalDate date;
    private String expenseGroup;

    public Expense(Long id, String description, BigDecimal amount, String person, String expenseGroup) {
        this(id, description, amount, Currency.getInstance("USD"), person, LocalDate.now(), expenseGroup);
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Expense(Long id, String description, BigDecimal amount, Currency currency, String person, LocalDate date, String expenseGroup) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.person = person;
        this.date = date;
        this.expenseGroup = expenseGroup;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getPerson() {
        return person;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getExpenseGroup()
    {
        return expenseGroup;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setExpenseGroup(String expenseGroup) {
        this.expenseGroup = expenseGroup;
    }
}
