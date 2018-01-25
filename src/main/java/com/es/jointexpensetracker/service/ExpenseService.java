package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class ExpenseService {
    private static ExpenseService instance;
    private Map<Long, Expense> expenses;

    private ExpenseService() {
        String expenseGroup = UUID.randomUUID().toString();
        List<Expense> tmp = Arrays.asList(
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
                new Expense(14L, "Bus tickets from Warsaw to Minsk", new BigDecimal(200), "Andrei", expenseGroup),
                new Expense(15L, "Test expense with another currency", new BigDecimal(500), Currency.getInstance("CNY"), "Egor", LocalDate.of(1999, 1, 20), expenseGroup)
        );
        // TODO for future: either ConcurrentHashMap or synchronized-write methods
        expenses = new HashMap<>();
        for (Expense expense : tmp)
            expenses.put(expense.getId(), expense);
    }

    public static synchronized ExpenseService getInstance(){
        if (instance != null)
            return instance;
        else
            return instance = new ExpenseService();
    }

    public Map<Long, Expense> getExpenseMap(){
        return expenses;
    }

    public Collection<Expense> getExpenses(){
        return expenses.values();
    }

    public Expense getExpenseById(long id){
        return expenses.get(id);
    }
}
