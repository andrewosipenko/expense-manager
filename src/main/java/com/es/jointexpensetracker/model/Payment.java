package com.es.jointexpensetracker.model;

import java.math.BigDecimal;

public class Payment
{
    private String payerName;
    private BigDecimal paymentAmount;

    public Payment(String payerName, BigDecimal paymentAmount)
    {
        this.payerName = payerName;
        this.paymentAmount = paymentAmount;
    }

    public String getPayerName()
    {
        return payerName;
    }

    public BigDecimal getPaymentAmount()
    {
        return paymentAmount;
    }

    public void setPayerName(String payerName)
    {
        this.payerName = payerName;
    }

    public void setPaymentAmount(BigDecimal paymentAmount)
    {
        this.paymentAmount = paymentAmount;
    }
}
