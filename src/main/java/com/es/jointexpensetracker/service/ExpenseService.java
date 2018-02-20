package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ExpenseService
{
    private ArrayList<Expense> expenses;
    private static volatile ExpenseService instance;

    private ExpenseService()
    {
        String expenseGroup = UUID.randomUUID().toString();
        expenses = new ArrayList<>(Arrays.asList(
                new Expense( "Train tickets from Minsk to Warsaw", new BigDecimal(200), "Andrei", expenseGroup),
                new Expense( "Air tickets from Warsaw to Gran Carania and back", new BigDecimal(2000), "Ivan", expenseGroup),
                new Expense( "Restaurant", new BigDecimal(90), "Andrei", expenseGroup),
                new Expense( "Rent a car", new BigDecimal(700), "Sergei", expenseGroup),
                new Expense( "Rent a car", new BigDecimal(500), "Igor", expenseGroup),
                new Expense( "Rent a house", new BigDecimal(2000), "Igor", expenseGroup),
                new Expense( "Restaurant", new BigDecimal(60), "Andrei", expenseGroup),
                new Expense( "Gazoline", new BigDecimal(50), "Sergei", expenseGroup),
                new Expense( "Gazoline", new BigDecimal(50), "Igor", expenseGroup),
                new Expense( "Surfing", new BigDecimal(30), "Sergei", expenseGroup),
                new Expense( "New year party shopping", new BigDecimal(30), "Igor", expenseGroup),
                new Expense( "Surfing", new BigDecimal(30), "Sergei", expenseGroup),
                new Expense( "Air wing", new BigDecimal(50), "Sergei", expenseGroup),
                new Expense( "Bus tickets from Warsaw to Minsk", new BigDecimal(200), "Andrei", expenseGroup)
        ));
    }

    public static ExpenseService getInstance()
    {
        if (instance == null)
        {
            synchronized (ExpenseService.class)
            {
                if (instance == null)
                {
                    instance = new ExpenseService();
                }
            }
        }
        return instance;
    }

    public Expense getExpense(Long id) throws IllegalArgumentException
    {
       return expenses.stream().filter(expense -> expense.getId().equals(id)).findAny().
               orElseThrow(() -> new IllegalArgumentException("There is no expense with id " + id));
    }

    public void addExpense(Expense newExpense)
    {
        expenses.add(newExpense);
    }

    public void deleteExpense(Long id) throws IllegalArgumentException
    {
        Expense expenseToDelete = getExpense(id);
        expenses.remove(expenseToDelete);
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }
}
