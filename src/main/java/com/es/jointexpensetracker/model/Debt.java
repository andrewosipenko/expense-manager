package com.es.jointexpensetracker.model;

import java.math.BigDecimal;

public class Debt{
    private String debtor;

    private String creditor;

    private BigDecimal amount;

    public Debt(String debtor, String creditor){
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = BigDecimal.ZERO;
    }

    public void processIncome(String contributor, BigDecimal value){
        if(contributor.equals(debtor)){
            pay(value);
        } else if(contributor.equals(creditor)){
            borrow(value);
        }
    }

    private void pay(BigDecimal value){
        amount = amount.subtract(value);
        if(amount.compareTo(BigDecimal.ZERO) < 0) swap();
    }

    private void borrow(BigDecimal value){
        amount = amount.add(value);
    }

    private void swap(){
        String buf = debtor;
        debtor = creditor;
        creditor = buf;
        amount = amount.negate();
    }

    public String getCreditor() {
        return creditor;
    }

    public String getDebtor() {
        return debtor;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
