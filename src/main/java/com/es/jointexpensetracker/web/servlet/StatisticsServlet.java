package com.es.jointexpensetracker.web.servlet;

import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Map<String, BigDecimal> paymentsMap = ExpenseService.getInstance().getExpenses().stream().collect(Collectors.toMap(
                Expense::getPerson, Expense::getAmount, (oldValue, newValue) -> (oldValue.add(newValue))));
        req.setAttribute("expenses", paymentsMap);
        req.setAttribute("debts", getDebtsList(paymentsMap));
        req.getRequestDispatcher("/WEB-INF/pages/statistics.jsp").forward(req, resp);
    }

    protected List<Debt> getDebtsList(Map<String, BigDecimal> paymentsMap)
    {
        if (paymentsMap.size() == 0)
        {
            return null;
        }
        else
        {
            List<Map.Entry<String, Double>> paymentsList = new ArrayList<>();
            paymentsMap.entrySet().forEach(
                    (entry) -> paymentsList.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().doubleValue()))
            );
            paymentsList.sort((Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) -> o1.getValue().compareTo(o2.getValue()));

            double sum = 0, avgSum;
            for (Map.Entry<String, Double> entry : paymentsList)
            {
                sum += entry.getValue();
            }
            avgSum = sum / paymentsList.size();
            List<Debt> debts = new ArrayList<>();
            for (int i = 0, j = paymentsList.size() - 1; i < j; )
            {
                Map.Entry<String, Double> debtorEntry = paymentsList.get(i);
                Map.Entry<String, Double> receiverEntry = paymentsList.get(j);
                double debt = avgSum - debtorEntry.getValue();
                double requiredAmount = receiverEntry.getValue() - avgSum;
                double paidAmount;
                if (debt == 0.)
                {
                    ++i;
                    continue;
                }
                else if (debt < requiredAmount)
                {
                    receiverEntry.setValue(receiverEntry.getValue() - debt);
                    ++i;
                    paidAmount = debt;
                }
                else if (debt > requiredAmount)
                {
                    debtorEntry.setValue(debtorEntry.getValue() + requiredAmount);
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
                        debtorEntry.getKey(),
                        new BigDecimal(paidAmount).setScale(2, BigDecimal.ROUND_HALF_UP),
                        receiverEntry.getKey()
                ));
            }
            return debts;
        }

    }

}