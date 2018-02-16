package com.es.jointexpensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class Expense {
    private final Long id;
    private final String expenseGroup;
    private volatile ConsistencyHelper helper;

    public Expense(Long id, String description, BigDecimal amount, String person, String expenseGroup) {
        this(id, description, amount, Currency.getInstance("USD"), person, LocalDate.now(), expenseGroup);
    }

    public Expense(Long id, String description, BigDecimal amount, Currency currency, String person, LocalDate date, String expenseGroup) {
        this.id = id;
        this.helper = new ConsistencyHelper(person, description, amount, currency, date);
        this.expenseGroup = expenseGroup;
    }

    public Long getId() {
        return id;
    }

    public String getExpenseGroup() {
        return expenseGroup;
    }

    public void update(String person, String description, BigDecimal amount, Currency currency, LocalDate date) {
        helper = new ConsistencyHelper(person, description, amount, currency, date);
    }

    public ConsistencyHelper getHelper() {
        return helper;
    }

    public static class ConsistencyHelper {
        private final String person;
        private final String description;
        private final BigDecimal amount;
        private final Currency currency;
        private final LocalDate date;

        private ConsistencyHelper(String person, String description, BigDecimal amount, Currency currency, LocalDate date) {
            this.description = description;
            this.amount = amount;
            this.currency = currency;
            this.person = person;
            this.date = date;
        }

        public String getPerson() {
            return person;
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

        public LocalDate getDate() {
            return date;
        }
    }
}
