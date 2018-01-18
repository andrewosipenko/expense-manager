package com.es.jointexpensetracker.service.impl;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.model.ExpenseGroup;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.exceptions.ExpenseGroupNotFoundException;

import java.math.BigDecimal;
import java.util.*;

public class HardcodeExpenseService implements ExpenseService {

    private static HardcodeExpenseService instance = new HardcodeExpenseService();

    private Map<String,ExpenseGroup> expenseGroupMap;

    private long nextId = 1;

    private HardcodeExpenseService(){
        Map<String,ExpenseGroup> expenseGroupMap = new HashMap<>();
        this.expenseGroupMap = Collections.synchronizedMap(expenseGroupMap);
        init();
    }

    private void init(){
        try {
            expenseGroupMap.put("hardcodeexpense", new ExpenseGroup("hardcodeexpense"));
            addNewExpense(new Expense("Train tickets from Minsk to Warsaw", new BigDecimal(200), "Andrei", "hardcodeexpense"));
            addNewExpense(new Expense("Air tickets from Warsaw to Gran Carania and back", new BigDecimal(2000), "Ivan", "hardcodeexpense"));
            addNewExpense(new Expense("Restaurant", new BigDecimal(90), "Andrei", "hardcodeexpense"));
            addNewExpense(new Expense("Rent a car", new BigDecimal(700), "Sergei", "hardcodeexpense"));
            addNewExpense(new Expense("Rent a car", new BigDecimal(500), "Igor", "hardcodeexpense"));
            addNewExpense(new Expense("Rent a house", new BigDecimal(2000), "Igor", "hardcodeexpense"));
            addNewExpense(new Expense("Restaurant", new BigDecimal(60), "Andrei", "hardcodeexpense"));
            addNewExpense(new Expense("Gazoline", new BigDecimal(50), "Sergei", "hardcodeexpense"));
            addNewExpense(new Expense("Gazoline", new BigDecimal(50), "Igor", "hardcodeexpense"));
            addNewExpense(new Expense("Surfing", new BigDecimal(30), "Sergei", "hardcodeexpense"));
            addNewExpense(new Expense("New year party shopping", new BigDecimal(30), "Igor", "hardcodeexpense"));
            addNewExpense(new Expense("Surfing", new BigDecimal(30), "Sergei", "hardcodeexpense"));
            addNewExpense(new Expense("Air wing", new BigDecimal(50), "Sergei", "hardcodeexpense"));
            addNewExpense(new Expense("Bus tickets from Warsaw to Minsk", new BigDecimal(200), "Andrei", "hardcodeexpense"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static HardcodeExpenseService getInstance(){
        return instance;
    }

    @Override
    public ExpenseGroup loadExpensesGroupById(String expenseGroupId) throws ExpenseGroupNotFoundException {
        ExpenseGroup expenseGroup = expenseGroupMap.get(expenseGroupId);
        if(expenseGroup == null){
            throw new ExpenseGroupNotFoundException();
        }
        return expenseGroup;
    }

    @Override
    public Optional<Expense> getExpenseById(long id, String expenseGroupId) throws ExpenseGroupNotFoundException {
        ExpenseGroup expenseGroup = loadExpensesGroupById(expenseGroupId);

        return expenseGroup.getExpenseList().stream()
                .filter(expense -> id == expense.getId())
                .findFirst();
    }

    @Override
    public boolean deleteExpenseById(long id,String expenseGroupId) throws ExpenseGroupNotFoundException {
        ExpenseGroup expenseGroup = loadExpensesGroupById(expenseGroupId);
        return expenseGroup.getExpenseList().removeIf(expense -> id == expense.getId());
    }

    private synchronized long getNextId(){
        return nextId++;
    }

    @Override
    public void addNewExpense(Expense newExpense) throws ExpenseGroupNotFoundException {
        ExpenseGroup expenseGroup = loadExpensesGroupById(newExpense.getExpenseGroup());
        newExpense.setId(getNextId());
        expenseGroup.getExpenseList().add(newExpense);
    }

    @Override
    public String createNewExpenseGroup() {
        String newExpenseGroup = UUID.randomUUID().toString().toLowerCase();
        expenseGroupMap.put(newExpenseGroup,new ExpenseGroup(newExpenseGroup));
        return newExpenseGroup;
    }
}