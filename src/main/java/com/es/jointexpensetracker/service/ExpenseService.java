package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.exception.ExpenseNotFoundException;
import com.es.jointexpensetracker.model.Expense;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseService {
    private List<Expense> expenses;
    private String expenseGroup;
    private static volatile ExpenseService expenseService;

    private ExpenseService(){
        expenseGroup = UUID.randomUUID().toString();
        expenses = new ArrayList<>();
        expenses.add( new Expense(1L, "Train tickets from Minsk to Warsaw", new BigDecimal(200), "Andrei", expenseGroup));
        expenses.add(new Expense(2L, "Air tickets from Warsaw to Gran Carania and back", new BigDecimal(2000), "Ivan", expenseGroup));
    }

    public Optional<Expense> getExpense(Long id){
        return  expenses.stream().filter(expense -> id.equals(expense.getId())).findFirst();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public String getExpenseGroup() {
        return expenseGroup;
    }

    public static ExpenseService getInstance(){
        if(expenseService == null){
            synchronized (ExpenseService.class) {
                if(expenseService == null) {
                    expenseService = new ExpenseService();
                }
            }
        }
        return expenseService;
    }

    public void updateExpense(Expense update) throws ExpenseNotFoundException {
       Optional<Expense> expense = expenseService.getExpense(update.getId());
       if(expense.isPresent()) {
           expense.get().setDescription(update.getDescription());
           expense.get().setAmount(update.getAmount());
           expense.get().setCurrency(update.getCurrency());
           expense.get().setPerson(update.getPerson());
           expense.get().setDate(update.getDate());
       } else {
           throw new ExpenseNotFoundException();
       }
    }

    public void delete(Long id) {
        expenses=expenses.stream().filter(expense -> !id.equals(expense.getId())).collect(Collectors.toList());
    }

    public void add(Expense expense) {
        expenses.add(expense);
    }
}

