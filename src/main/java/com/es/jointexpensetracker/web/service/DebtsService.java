package com.es.jointexpensetracker.web.service;

import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Payment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;


public class DebtsService
{
    public static List<Debt> getDebtsList(Map<String, BigDecimal> paymentsMap)
    {
        if (paymentsMap.size() == 0)
        {
            return new ArrayList<>();
        }
        else
        {
            List<Payment> paymentsList = paymentsMap.entrySet().stream().
                   map((entry) -> new Payment(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            paymentsList.sort(Comparator.comparing(Payment::getPaymentAmount));

            BigDecimal avgSum = getAverageSum(paymentsList);

            return calculateDebts(paymentsList, avgSum);
        }
    }

    private static BigDecimal getAverageSum(List<Payment> paymentsList)
    {
        BigDecimal sum = new BigDecimal(0);
        for (Payment payment : paymentsList)
        {
            sum = sum.add(payment.getPaymentAmount());
        }
        return sum.divide(new BigDecimal(paymentsList.size()), MathContext.DECIMAL32);
    }

    private static List<Debt> calculateDebts(List<Payment> paymentsList, BigDecimal avgSum)
    {
        List<Debt> debts = new ArrayList<>();
        for (int i = 0, j = paymentsList.size() - 1; i < j; )
        {
            Payment debtorPayment = paymentsList.get(i);
            Payment receiverPayment = paymentsList.get(j);
            BigDecimal debt =  avgSum.subtract(debtorPayment.getPaymentAmount());
            BigDecimal requiredAmount = receiverPayment.getPaymentAmount().subtract(avgSum);
            BigDecimal paidAmount;
            if (debt.doubleValue() == 0.)
            {
                ++i;
                continue;
            }
            else if (debt.compareTo(requiredAmount) < 0)
            {
                receiverPayment.setPaymentAmount(receiverPayment.getPaymentAmount().subtract(debt));
                ++i;
                paidAmount = debt;
            }
            else if (debt.compareTo(requiredAmount) > 0)
            {
                debtorPayment.setPaymentAmount(debtorPayment.getPaymentAmount().add(requiredAmount));
                --j;
                paidAmount = requiredAmount;
            }
            else
            {
                ++i;
                --j;
                paidAmount = debt;
            }
            debts.add(new Debt(
                    debtorPayment.getPayerName(),
                    paidAmount,
                    receiverPayment.getPayerName()
            ));
        }
        return debts;
    }
}
