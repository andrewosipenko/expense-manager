package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Debtor;
import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class DebtService {


    private static volatile DebtService debtService;
    private ExpenseService expenseService;

    private DebtService() {

        expenseService = ExpenseService.getInstance();

    }

    public static DebtService getInstance() {
        if (debtService == null) {
            synchronized (DebtService.class) {
                if (debtService == null) {
                    debtService = new DebtService();
                }
            }
        }
        return debtService;
    }

    private BigDecimal expenseSum() {
        return  BigDecimal.valueOf(
                expenseService
                .getExpenses()
                .stream()
                .map(Expense::getAmount)
                .collect(Collectors.toList())
                .stream()
                .mapToLong(BigDecimal::longValue)
                .sum()
        );
    }

    public BigDecimal expensePerPerson() {
        return expenseSum().divide(BigDecimal.valueOf(totalExpenses().size()),BigDecimal.ROUND_CEILING,2);
    }

    public Map<String, BigDecimal> totalExpenses() {
        return expenseService.getExpenses()
                .stream()
                .sorted()
                .collect(Collectors.toMap(Expense::getPerson,Expense::getAmount,BigDecimal::add));
    }

    public Map<String, BigDecimal> getDebtsMap() {
        Map<String, BigDecimal> debtsMap = totalExpenses();
        debtsMap.values().forEach((debt -> debt = debt.subtract(expensePerPerson())));
        return debtsMap;
    }

    public Set<Debtor> getDebtors() {
       Set<Debtor> debtors = new TreeSet<>();

        Map<String,BigDecimal> debtMap = getDebtsMap();
        Set<String> persons = debtMap.keySet();
        ArrayList<BigDecimal> personsDebts = new ArrayList<>(debtMap.values());

        Optional<BigDecimal> middle = personsDebts
                .stream()
                .filter(debt -> debt.compareTo(BigDecimal.ZERO) > 0)
                .findFirst();
        int middleIndex = 0;
        if(middle.isPresent()) {
            middleIndex = personsDebts.indexOf(middle.get());
        }
         //TODO: LOOP FOR CALCULATIONS END FILLING THE DEBTORS SET
        return debtors;
    }
}