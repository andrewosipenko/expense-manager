package com.es.jointexpensetracker.model;

import java.math.BigDecimal;

public class Debt {
    private final String creditorName;
    private final String debtorName;
    private final BigDecimal amount;

    public Debt(String creditorName, String debtorName, BigDecimal amount) {
        this.creditorName = creditorName;
        this.debtorName = debtorName;
        this.amount = amount;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
