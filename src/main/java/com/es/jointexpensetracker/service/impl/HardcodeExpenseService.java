package com.es.jointexpensetracker.service.impl;

import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

public class HardcodeExpenseService implements ExpenseService {

    private List<Expense> expenseList;

    private String expenseGroup = UUID.randomUUID().toString();

    private long nextId = 1;

    private static HardcodeExpenseService instance = new HardcodeExpenseService();

    public static HardcodeExpenseService getInstance(){
        return instance;
    }

    private HardcodeExpenseService(){
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(new Expense(getNextId(), "Train tickets from Minsk to Warsaw", new BigDecimal(200), "Andrei", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Air tickets from Warsaw to Gran Carania and back", new BigDecimal(2000), "Ivan", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Restaurant", new BigDecimal(90), "Andrei", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Rent a car", new BigDecimal(700), "Sergei", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Rent a car", new BigDecimal(500), "Igor", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Rent a house", new BigDecimal(2000), "Igor", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Restaurant", new BigDecimal(60), "Andrei", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Gazoline", new BigDecimal(50), "Sergei", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Gazoline", new BigDecimal(50), "Igor", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Surfing", new BigDecimal(30), "Sergei", expenseGroup));
        expenseList.add(new Expense(getNextId(), "New year party shopping", new BigDecimal(30), "Igor", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Surfing", new BigDecimal(30), "Sergei", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Air wing", new BigDecimal(50), "Sergei", expenseGroup));
        expenseList.add(new Expense(getNextId(), "Bus tickets from Warsaw to Minsk", new BigDecimal(200), "Andrei", expenseGroup));
        this.expenseList = Collections.synchronizedList(expenseList);
    }

    @Override
    public List<Expense> getExpenses() {
        return expenseList;
    }

    @Override
    public Optional<Expense> getExpenseById(long id){
        return expenseList.stream()
                .filter(expense -> id == expense.getId())
                .findFirst();
    }

    @Override
    public boolean deleteExpenseById(long id) {
        return expenseList.removeIf(expense -> id == expense.getId());
    }

    private synchronized long getNextId(){
        return nextId++;
    }

    @Override
    public void addNewExpense(Expense newExpense) {
        newExpense.setId(getNextId());
        newExpense.setExpenseGroup(expenseGroup);
        expenseList.add(newExpense);
    }

    @Override
    public Map<String,BigDecimal> getMapExpenses(){
        Map<String,BigDecimal> mapExpenses = expenseList.stream()
                .collect(Collectors.toMap(Expense::getPerson, Expense::getAmount, BigDecimal::add));
        return mapExpenses;
    }

    @Override
    public List<Debt> getDebts() {

        Map<String,BigDecimal> mapExpenses = getMapExpenses();
        BigDecimal sumOfExpenses = mapExpenses.values().stream()
                                            .reduce(BigDecimal::add)
                                            .orElse(BigDecimal.ZERO);

        BigDecimal expensePerPerson = sumOfExpenses.divide(new BigDecimal(mapExpenses.size()));

        List<Entry> listCreditors = mapExpenses.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(expensePerPerson)>0)
                .sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue()))
                .map(entry-> new Entry(
                        entry.getKey(),
                        entry.getValue()
                                .subtract(expensePerPerson)
                                .setScale(2, BigDecimal.ROUND_CEILING)))
                .collect(Collectors.toList());

        List<Entry> listDebtor = mapExpenses.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(expensePerPerson)<0)
                .sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue()))
                .map(entry-> new Entry(
                        entry.getKey(),
                        expensePerPerson
                                .subtract(entry.getValue())
                                .setScale(2, BigDecimal.ROUND_CEILING)))
                .collect(Collectors.toList());

        return createListDebts(listDebtor,listCreditors);
    }

    private List<Debt> createListDebts(List<Entry> listDebtor, List<Entry> listCreditors){
        Iterator<Entry> iteratorCreditors = listCreditors.iterator();
        Iterator<Entry> iteratorDebtor = listDebtor.iterator();

        List<Debt> debts = new ArrayList<>();

        boolean isHasNext = true;

        if(iteratorCreditors.hasNext()&&iteratorDebtor.hasNext()) {
            Entry debtor = iteratorDebtor.next();
            Entry creditor = iteratorCreditors.next();
            while(isHasNext){
                BigDecimal debt = debtor.amount;
                if (debt.compareTo(creditor.amount)<0){
                    debts.add(new Debt(debtor.person,debt,creditor.person));
                    creditor.amount = creditor.amount.subtract(debt);
                    isHasNext = iteratorDebtor.hasNext();
                    if(isHasNext){
                        debtor = iteratorDebtor.next();
                    }
                } else if(debt.compareTo(creditor.amount)>0){
                    debts.add(new Debt(debtor.person,creditor.amount,creditor.person));
                    debtor.amount = debtor.amount.subtract(creditor.amount);
                    isHasNext = iteratorCreditors.hasNext();
                    if(isHasNext){
                        creditor = iteratorCreditors.next();
                    }
                } else{
                    debts.add(new Debt(debtor.person,creditor.amount,creditor.person));
                    isHasNext = iteratorDebtor.hasNext() &&
                            iteratorCreditors.hasNext();
                    if(isHasNext){
                        debtor = iteratorDebtor.next();
                        creditor = iteratorCreditors.next();
                    }
                }
            }
        }

        return debts;
    }

    private class Entry{
        private String person;
        private BigDecimal amount;

        public Entry(String person, BigDecimal amount) {
            this.person = person;
            this.amount = amount;
        }
    }
}
