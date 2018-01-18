package com.es.jointexpensetracker.service.impl;


import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.model.ExpenseGroup;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.StatisticService;
import com.es.jointexpensetracker.service.exceptions.ExpenseGroupNotFoundException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticServiceImpl implements StatisticService {

    private static StatisticServiceImpl instance = new StatisticServiceImpl();
    private ExpenseService expenseService;

    private StatisticServiceImpl(){
        expenseService = HardcodeExpenseService.getInstance();
    }

    public static StatisticServiceImpl getInstance(){
        return instance;
    }

    @Override
    public Map<String,BigDecimal> loadExpensesMapByExpenseGroupId(String expenseGroupId) throws ExpenseGroupNotFoundException {
        ExpenseGroup expenseGroup = expenseService.loadExpensesGroupById(expenseGroupId);
        if(expenseGroup == null){
            return null;
        }
        Map<String,BigDecimal> expensesMap = expenseGroup.getExpenseList().stream()
                .collect(Collectors.toMap(Expense::getPerson, Expense::getAmount, BigDecimal::add));
        return expensesMap;
    }

    @Override
    public List<Debt> loadDebtsByExpenseGroupId(String expenseGroupId) throws ExpenseGroupNotFoundException {
        Map<String,BigDecimal> expensesMap = loadExpensesMapByExpenseGroupId(expenseGroupId);
        if(expensesMap == null){
            return null;
        }
        BigDecimal sumOfExpenses = expensesMap.values().stream()
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        final BigDecimal expensePerPerson;

        if(expensesMap.size() != 0) {
            expensePerPerson = sumOfExpenses.divide(new BigDecimal(expensesMap.size()),new MathContext(20, RoundingMode.CEILING));
        }else{
            expensePerPerson = BigDecimal.ZERO;
        }

        List<Entry> creditorsList = expensesMap.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(expensePerPerson)>0)
                .sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue()))
                .map(entry-> new Entry(
                        entry.getKey(),
                        entry.getValue()
                                .subtract(expensePerPerson)
                                .setScale(2, BigDecimal.ROUND_CEILING)))
                .collect(Collectors.toList());

        List<Entry> debtorsList = expensesMap.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(expensePerPerson)<0)
                .sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue()))
                .map(entry-> new Entry(
                        entry.getKey(),
                        expensePerPerson
                                .subtract(entry.getValue())
                                .setScale(2, BigDecimal.ROUND_CEILING)))
                .collect(Collectors.toList());

        return createDebtsList(debtorsList,creditorsList);
    }

    private List<Debt> createDebtsList(List<Entry> debtorsList, List<Entry> creditorsList){
        Iterator<Entry> creditorsIterator = creditorsList.iterator();
        Iterator<Entry> debtorIterator = debtorsList.iterator();

        List<Debt> debts = new ArrayList<>();

        boolean isHasNext = true;

        if(creditorsIterator.hasNext()&&debtorIterator.hasNext()) {
            Entry debtor = debtorIterator.next();
            Entry creditor = creditorsIterator.next();
            while(isHasNext){
                BigDecimal debt = debtor.amount;
                if (debt.compareTo(creditor.amount)<0){
                    debts.add(new Debt(debtor.person,debt,creditor.person));
                    creditor.amount = creditor.amount.subtract(debt);
                    isHasNext = debtorIterator.hasNext();
                    if(isHasNext){
                        debtor = debtorIterator.next();
                    }
                } else if(debt.compareTo(creditor.amount)>0){
                    debts.add(new Debt(debtor.person,creditor.amount,creditor.person));
                    debtor.amount = debtor.amount.subtract(creditor.amount);
                    isHasNext = creditorsIterator.hasNext();
                    if(isHasNext){
                        creditor = creditorsIterator.next();
                    }
                } else{
                    debts.add(new Debt(debtor.person,creditor.amount,creditor.person));
                    isHasNext = debtorIterator.hasNext() &&
                            creditorsIterator.hasNext();
                    if(isHasNext){
                        debtor = debtorIterator.next();
                        creditor = creditorsIterator.next();
                    }
                }
            }
        }

        return debts;
    }

    private class Entry{
        private String person;
        private BigDecimal amount;

        Entry(String person, BigDecimal amount) {
            this.person = person;
            this.amount = amount;
        }
    }
}
