package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Debtor;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.model.PersonExpense;

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

    private Optional<BigDecimal> expenseSum() {
        return  expenseService
                .getExpenses()
                .stream()
                .map(Expense::getAmount)
                .collect(Collectors.toList())
                .stream()
                .reduce(BigDecimal::add);
    }

    private BigDecimal expensePerPerson(final int amount) {
        return expenseSum().isPresent() ?
                expenseSum()
                        .get()
                        .divide(BigDecimal.valueOf(amount), BigDecimal.ROUND_CEILING,2)
                : BigDecimal.ZERO;
    }

    private Map<String, BigDecimal> getTotalExpenses() {
        return   expenseService.getExpenses()
                .stream()
                .collect(Collectors.toMap(Expense::getPerson, Expense::getAmount, BigDecimal::add))
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

    public List<PersonExpense> getPersonExpenseList() {
     return  getTotalExpenses()
             .entrySet()
             .stream()
             .map(e -> new PersonExpense(e.getKey(),e.getValue()))
             .collect(Collectors.toList());
    }

    public List<Debtor> getDebtors() {
        List<Debtor> debtors = new ArrayList<>();
        Map<String, BigDecimal> totalExpenses = getTotalExpenses();
        ArrayList<Map.Entry<String, BigDecimal>> debts = new ArrayList<>(totalExpenses.entrySet());
        BigDecimal expensePerPerson = expensePerPerson(totalExpenses.size());
        int middleIndex = getMiddle(debts, expensePerPerson);

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
            DebtPair debtPair = setDebtor(debts, debtors, debt, credit, expensePerPerson);
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

    private void updateDebtAndCredit(List<Map.Entry<String,BigDecimal>> debts,
                                     List<Debtor> debtors,
                                     BigDecimal debtValue,
                                     Map.Entry<String,BigDecimal> oldDebt,
                                     Map.Entry<String,BigDecimal> newDebt,
                                     Map.Entry<String,BigDecimal> oldCredit,
                                     Map.Entry<String,BigDecimal> newCredit) {
        debtors.add(new Debtor(oldDebt.getKey(), debtValue, oldCredit.getKey()));
        debts.set(debts.indexOf(oldDebt), newDebt);
        debts.set(debts.indexOf(oldCredit), newCredit);
    }

    private DebtPair setDebtor(List<Map.Entry<String,BigDecimal>> debts,
                               List<Debtor> debtors,
                               Map.Entry<String,BigDecimal> debt,
                               Map.Entry<String,BigDecimal> credit,
                               BigDecimal expensePerPerson) {
        Map.Entry<String,BigDecimal> newDebt;
        Map.Entry<String,BigDecimal> newCredit;
        if(debt.getValue().equals(credit.getValue()) && !debt.getKey().equals(credit.getKey())) {
            newDebt = new AbstractMap.SimpleEntry<>(debt.getKey(), expensePerPerson);
            newCredit = new AbstractMap.SimpleEntry<>(credit.getKey(), expensePerPerson);
            updateDebtAndCredit(debts, debtors, expensePerPerson.subtract(debt.getValue()), debt, newDebt, credit, newCredit);
        } else {
            if(debt.getValue().add(credit.getValue()).compareTo(expensePerPerson.multiply(BigDecimal.valueOf(2))) < 0) {
                BigDecimal curDebt = credit.getValue().subtract(expensePerPerson);
                newDebt = new AbstractMap.SimpleEntry<>(debt.getKey(), debt.getValue().add(curDebt));
                newCredit = new AbstractMap.SimpleEntry<>(credit.getKey(), expensePerPerson);
                updateDebtAndCredit(debts, debtors, curDebt, debt, newDebt, credit, newCredit);
            } else {
                BigDecimal curDebt = expensePerPerson.subtract(debt.getValue());
                newDebt = new AbstractMap.SimpleEntry<>(debt.getKey(), expensePerPerson);
                newCredit = new AbstractMap.SimpleEntry<>(credit.getKey(), credit.getValue().subtract(curDebt));
                updateDebtAndCredit(debts, debtors, curDebt, debt, newDebt, credit, newCredit);
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