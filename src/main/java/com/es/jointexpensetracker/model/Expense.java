package com.es.jointexpensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class Expense {
    private Long id;
    private String description;
    private BigDecimal amount;
    private Currency currency;
    private String person;
    private LocalDate date;

    public Expense() {
    }

    public Expense(Long id, String description, BigDecimal amount, String person) {
        this(id, description, amount, Currency.getInstance("USD"), person, LocalDate.now());
    }

    public Expense(Long id, String description, BigDecimal amount, Currency currency, String person, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.person = person;
        this.date = date;
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
}
