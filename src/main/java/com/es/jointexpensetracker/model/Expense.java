package com.es.jointexpensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class Expense {
    private final Long id;
    private final String description;
    private final BigDecimal amount;
    private final Currency currency;
    private final String person;
    private final LocalDate date;
    private final String expenseGroup;

    public Expense(Long id, String description, BigDecimal amount, String person, String expenseGroup) {
        this(id, description, amount, Currency.getInstance("USD"), person, LocalDate.now(), expenseGroup);
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
}
