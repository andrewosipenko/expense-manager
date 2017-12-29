package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ExpenseServiceSingleton implements ExpenseService {

    private static ExpenseServiceSingleton instance;

    public List<Expense> expenses;


    private ExpenseServiceSingleton(){
        initExpenses();
    }

    public static ExpenseServiceSingleton getInstance(){
        if(instance == null){
            instance = new ExpenseServiceSingleton();
        }
        return instance;
    }

    private void initExpenses() {
        String expenseGroup = UUID.randomUUID().toString();
        expenses = Arrays.asList(
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
        );
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
