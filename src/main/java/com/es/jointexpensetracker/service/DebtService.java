
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

    private BigDecimal expensePerPerson() {
        return expenseSum().divide(BigDecimal.valueOf(getTotalExpenses().size()),BigDecimal.ROUND_CEILING,2);
    }

    public LinkedHashMap<String, BigDecimal> getTotalExpenses() {
        return   expenseService.getExpenses()
                .stream()
                .collect(Collectors.toMap(Expense::getPerson,Expense::getAmount,BigDecimal::add))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public ArrayList<Debtor> getDebtors() {

        ArrayList<Debtor> debtors = new ArrayList<>();
        ArrayList<Map.Entry<String,BigDecimal>> debts = new ArrayList<>(getTotalExpenses().entrySet());
        BigDecimal expensePerPerson = expensePerPerson();
        int middleIndex = getMiddle(debts,expensePerPerson);

        ListIterator<Map.Entry<String,BigDecimal>> begin = debts.listIterator();
        ListIterator<Map.Entry<String,BigDecimal>> end = debts.listIterator(debts.size());
        Map.Entry<String,BigDecimal> debt = begin.next();
        Map.Entry<String,BigDecimal> credit = end.previous();

        while (begin.nextIndex()  <= middleIndex && end.previousIndex() >= middleIndex - 1) {
            if (debt.getValue().compareTo(expensePerPerson) >= 0) {
                debt = begin.next();
            }
            if (credit.getValue().compareTo(expensePerPerson) <= 0) {
                credit = end.previous();
            }
            if (debts.indexOf(debt) == middleIndex || debts.indexOf(credit) < middleIndex) {
                break;
            }
            DebtPair debtPair = setDebtor(debts, debtors,debt,credit);
            debt = debtPair.getDebt();
            credit = debtPair.getCredit();
        }
        return debtors;
    }

    private int getMiddle(ArrayList<Map.Entry<String,BigDecimal>> debts, BigDecimal expensePerPerson){
        Optional<Map.Entry<String,BigDecimal>> middle = debts
                .stream()
                .filter(debt -> debt.getValue().compareTo(expensePerPerson) > 0)
                .findFirst();
        int middleIndex = 0;
        if(middle.isPresent()) {
            middleIndex = debts.indexOf(middle.get());
        }
        return middleIndex;
    }

    private void updateDebtAndCredit(ArrayList<Map.Entry<String,BigDecimal>> debts,
                                     ArrayList<Debtor> debtors,
                                     BigDecimal debtValue,
                                     Map.Entry<String,BigDecimal> oldDebt,
                                     Map.Entry<String,BigDecimal> newDebt,
                                     Map.Entry<String,BigDecimal> oldCredit,
                                     Map.Entry<String,BigDecimal> newCredit) {
        debtors.add(new Debtor(oldDebt.getKey(),debtValue,oldCredit.getKey()));
        debts.set(debts.indexOf(oldDebt), newDebt);
        debts.set(debts.indexOf(oldCredit), newCredit);

    }

    private DebtPair setDebtor(ArrayList<Map.Entry<String,BigDecimal>> debts,
                               ArrayList<Debtor> debtors,
                               Map.Entry<String,BigDecimal> debt,
                               Map.Entry<String,BigDecimal> credit) {
        BigDecimal expensePerPerson = expensePerPerson();
        Map.Entry<String,BigDecimal> newDebt;
        Map.Entry<String,BigDecimal> newCredit;
        if(debt.getValue().equals(credit.getValue()) && !debt.getKey().equals(credit.getKey())) {
            newDebt = new AbstractMap.SimpleEntry<>(debt.getKey(),expensePerPerson);
            newCredit = new AbstractMap.SimpleEntry<>(credit.getKey(),expensePerPerson);
            updateDebtAndCredit(debts,debtors,expensePerPerson.subtract(debt.getValue()),debt,newDebt,credit,newCredit);
        } else {
            if(debt.getValue().add(credit.getValue()).compareTo(expensePerPerson.multiply(BigDecimal.valueOf(2))) < 0) {
                BigDecimal curDebt = credit.getValue().subtract(expensePerPerson);
                newDebt = new AbstractMap.SimpleEntry<>(debt.getKey(),debt.getValue().subtract(curDebt));
                newCredit = new AbstractMap.SimpleEntry<>(credit.getKey(),expensePerPerson);
                updateDebtAndCredit(debts,debtors,curDebt,debt,newDebt,credit,newCredit);
            } else {
                BigDecimal curDebt = expensePerPerson.subtract(debt.getValue());
                newDebt = new AbstractMap.SimpleEntry<>(debt.getKey(),debt.getValue().subtract(curDebt));
                newCredit = new AbstractMap.SimpleEntry<>(credit.getKey(),expensePerPerson);
                updateDebtAndCredit(debts,debtors,curDebt,debt,newDebt,credit,newCredit);
            }
        }
        return new DebtPair(newDebt,newCredit);
    }

    private class DebtPair {
        Map.Entry<String,BigDecimal> debt;
        Map.Entry<String,BigDecimal> credit;

        public Map.Entry<String, BigDecimal> getDebt() {
            return debt;
        }

        public Map.Entry<String, BigDecimal> getCredit() {
            return credit;
        }

        public DebtPair(Map.Entry<String, BigDecimal> debt, Map.Entry<String, BigDecimal> credit) {

            this.debt = debt;
            this.credit = credit;
        }
    }
}