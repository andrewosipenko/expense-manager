package com.es.jointexpensetracker.service.impl;

import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

public class HardcodeExpenseService extends MemoryExpenseService {

    public HardcodeExpenseService(){
        super("hardcodeexpense");
        addNewExpense(new Expense("Train tickets from Minsk to Warsaw", new BigDecimal(200), "Andrei"));
        addNewExpense(new Expense("Air tickets from Warsaw to Gran Carania and back", new BigDecimal(2000), "Ivan"));
        addNewExpense(new Expense("Restaurant", new BigDecimal(90), "Andrei"));
        addNewExpense(new Expense("Rent a car", new BigDecimal(700), "Sergei"));
        addNewExpense(new Expense("Rent a car", new BigDecimal(500), "Igor"));
        addNewExpense(new Expense("Rent a house", new BigDecimal(2000), "Igor"));
        addNewExpense(new Expense("Restaurant", new BigDecimal(60), "Andrei"));
        addNewExpense(new Expense("Gazoline", new BigDecimal(50), "Sergei"));
        addNewExpense(new Expense("Gazoline", new BigDecimal(50), "Igor"));
        addNewExpense(new Expense("Surfing", new BigDecimal(30), "Sergei"));
        addNewExpense(new Expense("New year party shopping", new BigDecimal(30), "Igor"));
        addNewExpense(new Expense("Surfing", new BigDecimal(30), "Sergei"));
        addNewExpense(new Expense("Air wing", new BigDecimal(50), "Sergei"));
        addNewExpense(new Expense("Bus tickets from Warsaw to Minsk", new BigDecimal(200), "Andrei"));
    }
}
