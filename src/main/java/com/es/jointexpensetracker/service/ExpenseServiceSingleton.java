package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.exception.DataNotFoundException;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.model.ExpenseKey;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseServiceSingleton implements ExpenseService {

    private List<Expense> expenses;
    private List<String> expenseGroups;

    private long lastId;
    private String expenseGroup;

    private final static ExpenseServiceSingleton instance = new ExpenseServiceSingleton();

    private ExpenseServiceSingleton() {
        initExpenseGroups();
        initExpenses();
        this.lastId = this.expenses.size();
    }

    public static ExpenseServiceSingleton getInstance() {
        return instance;
    }

    private void initExpenseGroups(){
        this.expenseGroup = UUID.randomUUID().toString();
        System.out.println("ExpenseServiceSingleton: expenseGroup="+this.expenseGroup);
        this.expenseGroups = new LinkedList<>();
        expenseGroups.add(expenseGroup);
    }

    private void initExpenses() {
        expenses = new LinkedList<>(Arrays.asList(
                new Expense(1L, "Train tickets from Minsk to Warsaw", new BigDecimal(200), "Andrei", expenseGroup),
                new Expense(2L, "Air tickets from Warsaw to Gran Carania and back", new BigDecimal(2000), "Ivan", expenseGroup),
                new Expense(3L, "Restaurant", new BigDecimal(90), "Andrei", expenseGroup),
                new Expense(4L, "Rent a car", new BigDecimal(700), "Sergei", expenseGroup),
                new Expense(5L, "Rent a car", new BigDecimal(500), "Igor", expenseGroup),
                new Expense(6L, "Rent a house", new BigDecimal(2000), "Igor", expenseGroup),
                new Expense(7L, "Restaurant", new BigDecimal(60), "Andrei", expenseGroup),
                new Expense(8L, "Gazoline", new BigDecimal(50), "Sergei", expenseGroup),
                new Expense(9L, "Gazoline", new BigDecimal(50), "Igor", expenseGroup),
                new Expense(10L, "Surfing", new BigDecimal(30), "Sergei", expenseGroup),
                new Expense(11L, "New year party shopping", new BigDecimal(30), "Igor", expenseGroup),
                new Expense(12L, "Surfing", new BigDecimal(30), "Sergei", expenseGroup),
                new Expense(13L, "Air wing", new BigDecimal(50), "Sergei", expenseGroup),
                new Expense(14L, "Bus tickets from Warsaw to Minsk", new BigDecimal(200), "Andrei", expenseGroup)
        ));
    }

    @Override
    public List<Expense> getExpensesByGroup(String expenseGroup) {
        if (!expenseGroups.contains(expenseGroup)) {
            throw new DataNotFoundException("Can't find expense group: " + expenseGroup);
        }
        return expenses.stream()
                .filter(e -> e.getExpenseGroup().equals(expenseGroup))
                .collect(Collectors.toList());
    }

    @Override
    public Expense loadExpenseByKey(ExpenseKey key) throws DataNotFoundException {
        return expenses.stream()
                .filter((a) -> a.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Can't find expense"));
    }

    @Override
    public Expense addExpense(String expenseGroup) {
        Expense expense = new Expense();
        expense.setKey(new ExpenseKey(++lastId, expenseGroup));
        expense.setDate(LocalDate.now());
        expense.setPerson("");
        expense.setDescription("");
        expense.setCurrency(Currency.getInstance("USD"));
        expense.setAmount(BigDecimal.ZERO);
        expenses.add(expense);
        return expense;
    }

    @Override
    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }

    @Override
    public void save() {
        //saving
    }

    @Override
    public String createExpenseGroup(){
        String newExpenseGroup;
        do {
            newExpenseGroup = UUID.randomUUID().toString();
        } while (expenseGroups.contains(newExpenseGroup));
        expenseGroups.add(newExpenseGroup);
        return newExpenseGroup;
    }
}
