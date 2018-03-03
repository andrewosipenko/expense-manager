package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.exception.ExpenseNotFoundException;
import com.es.jointexpensetracker.model.Expense;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static com.es.jointexpensetracker.constants.Constants.DEFAULT_EXPENSE_GROUP;

public class ExpenseService {
    private Map<String, List<Expense>> expenseMap;
    private static ExpenseService expenseService = new ExpenseService();

    private ExpenseService() {
        expenseMap = new ConcurrentHashMap<>();
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
    }

    public Optional<Expense> getExpense(Long id, String groupId){
        return  Collections.synchronizedList(expenseMap.get(groupId))
                .stream()
                .filter(expense -> id.equals(expense.getId()))
                .findFirst();
    }

    public static ExpenseService getInstance(){
        return expenseService;
    }

    public void updateExpense(Expense update, String groupId) throws ExpenseNotFoundException {
       Optional<Expense> expense = expenseService.getExpense(update.getId(), groupId);
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

    public void delete(Long id, String groupId) {
        expenseMap.put(groupId, expenseMap.get(groupId)
                .stream()
                .filter(expense -> !id.equals(expense.getId()))
                .collect(Collectors.toList()));
    }

    public void add(Expense expense, String groupId) {
        Collections.synchronizedList(expenseMap.get(groupId)).add(expense);
    }

    public void addExpenseGroup(String newExpenseGroupId ) {
        expenseMap.put(newExpenseGroupId, new ArrayList<>());
    }

    public List<Expense> getExpensesByGroupId(String groupId) {
        return Collections.synchronizedList(expenseMap.get(groupId));
    }
}

