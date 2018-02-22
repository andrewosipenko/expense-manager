package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.util.*;

public class ExpenseService
{
    private Map<String, List<Expense>> expenseGroups;
    private static volatile ExpenseService instance;

    private ExpenseService()
    {
        expenseGroups = new HashMap<>();
        String expenseGroup = "testData";
        expenseGroups.put(expenseGroup, new ArrayList<>(Arrays.asList(
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
        )));
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

    public Expense getExpense(String uuid, Long id) throws IllegalArgumentException
    {
       return expenseGroups.get(uuid).stream().filter(expense -> expense.getId().equals(id)).findAny().
               orElseThrow(() -> new IllegalArgumentException("There is no expense with id " + id));
    }

    public void addExpense(String uuid ,Expense newExpense) throws IllegalArgumentException

    {
        expenseGroups.get(uuid).add(newExpense);
    }

    public void addExpenseGroup(String uuid)
    {
        expenseGroups.put(uuid, new ArrayList<>());
    }


    public void deleteExpense(String uuid, Long id) throws IllegalArgumentException
    {
        Expense expenseToDelete = getExpense(uuid, id);
        expenseGroups.get(uuid).remove(expenseToDelete);
    }

    public List<Expense> getExpenses(String uuid) throws IllegalArgumentException
    {
        List<Expense> expenses = expenseGroups.get(uuid);
        if (expenses != null)
        {
            return expenses;
        }
        else
        {
            throw new IllegalArgumentException("There is no such group with uuid " + uuid);
        }
    }

    public boolean containsGroup(String uuid)
    {
        return expenseGroups.containsKey(uuid);
    }

}
