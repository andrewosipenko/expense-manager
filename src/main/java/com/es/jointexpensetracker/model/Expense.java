package com.es.jointexpensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

public class Expense {
    private ExpenseKey key;
    private String description;
    private BigDecimal amount;
    private Currency currency;
    private String person;
    private LocalDate date;

    public Expense(){
    }

    public Expense(Long id, String description, BigDecimal amount, String person, String expenseGroup) {
        this(id, description, amount, Currency.getInstance("USD"), person, LocalDate.now(), expenseGroup);
    }

    public Expense(Long id, String description, BigDecimal amount, Currency currency, String person, LocalDate date, String expenseGroup) {
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.person = person;
        this.date = date;
        this.key = new ExpenseKey(id, expenseGroup);
    }

    public Long getId(){
        return key.getId();
    }

    public String getExpenseGroup(){
        return key.getExpenseGroup();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setKey(ExpenseKey key){
        this.key = key;
    }

    public ExpenseKey getKey(){
        return key;
    }

    public static class ExpenseKey {
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
    }
}
