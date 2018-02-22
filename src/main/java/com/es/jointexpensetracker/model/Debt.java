package com.es.jointexpensetracker.model;

import java.math.BigDecimal;

public class Debt
{
    private String debtor;
    private BigDecimal amount;
    private String receiver;

    public Debt(String debtor,BigDecimal amount, String receiver)
    {
        this.debtor=debtor;
        this.amount=amount;
        this.receiver=receiver;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDebtor() {
        return debtor;
    }

    public String getReceiver() {
        return receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
