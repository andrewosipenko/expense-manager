package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.exception.ExpenseNotFoundException;
import com.es.jointexpensetracker.model.Expense;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseService {
    private Map<String, List<Expense>> expenseMap;
    public static final String DEFAULT_EXPENSE_GROUP = "default-expense-group";
    private static volatile ExpenseService expenseService;
    private List<Expense> customGroupExpenses;

    private ExpenseService(String groupId) {
        expenseMap = new HashMap<>();
        expenseMap.put(DEFAULT_EXPENSE_GROUP, new ArrayList<>());
        List<Expense> expenses = expenseMap.get(DEFAULT_EXPENSE_GROUP);

        expenses.add( new Expense(1L, "Train tickets from Minsk to Warsaw", new BigDecimal(200), "Andrei", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(2L, "Air tickets from Warsaw to Gran Carania and back", new BigDecimal(2000), "Ivan", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(3L, "Restaurant", new BigDecimal(90), "Andrei", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(4L, "Rent a car", new BigDecimal(700), "Sergei", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(5L, "Rent a car", new BigDecimal(500), "Igor", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(6L, "Rent a house", new BigDecimal(2000), "Igor", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(7L, "Restaurant", new BigDecimal(60), "Andrei", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(8L, "Gazoline", new BigDecimal(50), "Sergei", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(9L, "Gazoline", new BigDecimal(50), "Igor", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(10L, "Surfing", new BigDecimal(30), "Sergei", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(11L, "New year party shopping", new BigDecimal(30), "Igor", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(12L, "Surfing", new BigDecimal(30), "Sergei", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(13L, "Air wing", new BigDecimal(50), "Sergei", DEFAULT_EXPENSE_GROUP));
        expenses.add(new Expense(14L, "Bus tickets from Warsaw to Minsk", new BigDecimal(200), "Andrei", DEFAULT_EXPENSE_GROUP));

        customGroupExpenses = expenseMap.get(groupId);
    }

    public Optional<Expense> getExpense(Long id){
        return  customGroupExpenses.stream().filter(expense -> id.equals(expense.getId())).findFirst();
    }

    public List<Expense> getCustomGroupExpenses() {
        return customGroupExpenses;
    }

    public static ExpenseService getInstance(String groupId){
        if(expenseService == null){
            synchronized (ExpenseService.class) {
                if(expenseService == null) {
                    expenseService = new ExpenseService(groupId);
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
        customGroupExpenses = customGroupExpenses.stream().filter(expense -> !id.equals(expense.getId())).collect(Collectors.toList());
    }

    public void add(Expense expense) {
        customGroupExpenses.add(expense);
    }
}

