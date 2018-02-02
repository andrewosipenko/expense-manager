package com.es.jointexpensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;


public class Expense {
    private final static AtomicLong atomicCounter = new AtomicLong(0);
    private final Long id;
    private String description;
    private BigDecimal amount;
    private Currency currency;
    private String person;
    private LocalDate date;
    private final String expenseGroup;

    public Expense()
    {
        id = getNewID();
        expenseGroup = UUID.randomUUID().toString();
    }

    public Expense(String description, BigDecimal amount, String person, String expenseGroup) {
        this(description, amount, Currency.getInstance("USD"), person, LocalDate.now(), expenseGroup);
    }

    public Expense(String description, BigDecimal amount, Currency currency, String person, LocalDate date, String expenseGroup) {
        this.id = getNewID();
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.person = person;
        this.date = date;
        this.expenseGroup = expenseGroup;

    }

    private Long getNewID()
    {
        return atomicCounter.addAndGet(1);
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}


