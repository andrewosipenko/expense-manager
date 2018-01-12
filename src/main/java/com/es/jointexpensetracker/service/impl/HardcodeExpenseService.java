package com.es.jointexpensetracker.service.impl;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import java.math.BigDecimal;
import java.util.*;

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
}
