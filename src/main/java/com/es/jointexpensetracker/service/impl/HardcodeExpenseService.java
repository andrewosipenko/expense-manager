package com.es.jointexpensetracker.service.impl;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import java.math.BigDecimal;
import java.util.*;

public class HardcodeExpenseService implements ExpenseService {

    private List<Expense> expenseList;

    private static HardcodeExpenseService instance = new HardcodeExpenseService();

    public static HardcodeExpenseService getInstance(){
        return instance;
    }

    private HardcodeExpenseService(){
        String expenseGroup = UUID.randomUUID().toString();
        expenseList = new ArrayList<>();
        expenseList.add(new Expense(1L, "Train tickets from Minsk to Warsaw", new BigDecimal(200), "Andrei", expenseGroup));
        expenseList.add(new Expense(2L, "Air tickets from Warsaw to Gran Carania and back", new BigDecimal(2000), "Ivan", expenseGroup));
        expenseList.add(new Expense(3L, "Restaurant", new BigDecimal(90), "Andrei", expenseGroup));
        expenseList.add(new Expense(4L, "Rent a car", new BigDecimal(700), "Sergei", expenseGroup));
        expenseList.add(new Expense(5L, "Rent a car", new BigDecimal(500), "Igor", expenseGroup));
        expenseList.add(new Expense(6L, "Rent a house", new BigDecimal(2000), "Igor", expenseGroup));
        expenseList.add(new Expense(7L, "Restaurant", new BigDecimal(60), "Andrei", expenseGroup));
        expenseList.add(new Expense(8L, "Gazoline", new BigDecimal(50), "Sergei", expenseGroup));
        expenseList.add(new Expense(9L, "Gazoline", new BigDecimal(50), "Igor", expenseGroup));
        expenseList.add(new Expense(10L, "Surfing", new BigDecimal(30), "Sergei", expenseGroup));
        expenseList.add(new Expense(11L, "New year party shopping", new BigDecimal(30), "Igor", expenseGroup));
        expenseList.add(new Expense(12L, "Surfing", new BigDecimal(30), "Sergei", expenseGroup));
        expenseList.add(new Expense(13L, "Air wing", new BigDecimal(50), "Sergei", expenseGroup));
        expenseList.add(new Expense(14L, "Bus tickets from Warsaw to Minsk", new BigDecimal(200), "Andrei", expenseGroup));
        expenseList = Collections.synchronizedList(expenseList);
    }

    @Override
    public List<Expense> getExpenses() {
        return expenseList;
    }

    @Override
    public Optional<Expense> getExpenseById(int id){
        return expenseList.stream()
                .filter(expense -> id == expense.getId())
                .findFirst();
    }

    @Override
    public boolean deleteExpenseById(int id) {
        return expenseList.removeIf(expense -> id == expense.getId());
    }
}
