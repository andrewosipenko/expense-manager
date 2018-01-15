package com.es.jointexpensetracker.model;


import java.math.BigDecimal;
import java.util.Currency;

public class Debt {
    private String debtor;
    private BigDecimal amount;
    private Currency currency;
    private String creditor;

    public Debt(String debtor, BigDecimal amount, String creditor){
        this(debtor,amount,Currency.getInstance("USD"),creditor);
    }

    public Debt(String debtor, BigDecimal amount, Currency currency, String creditor) {
        this.debtor = debtor;
        this.amount = amount;
        this.currency = currency;
        this.creditor = creditor;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }
}
