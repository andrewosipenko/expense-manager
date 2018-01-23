package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.exception.DataNotFoundException;
import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class ExpenseServiceSingleton implements ExpenseService {

    private ArrayList<Expense> expenses;

    private long lastId;
    private String expenseGroup;

    private final static ExpenseServiceSingleton instance = new ExpenseServiceSingleton();

    private ExpenseServiceSingleton() {
        initExpenses();
        this.lastId = this.expenses.size();
    }

    public static ExpenseServiceSingleton getInstance() {
        return instance;
    }

    private void initExpenses() {
        this.expenseGroup = UUID.randomUUID().toString();
        expenses = new ArrayList<>(Arrays.asList(
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
    public List<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public Expense loadExpenseById(long id) throws DataNotFoundException {
        return expenses.stream()
                .filter((a) -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Can't find expense with id="+id));
    }

    @Override
    public Expense createExpense() {
        Expense expense = new Expense();
        expense.setId(++lastId);
        expense.setExpenseGroup(expenseGroup);
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
}
