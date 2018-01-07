package com.es.jointexpensetracker.service.impl;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;

import java.math.BigDecimal;
import java.util.*;

public class HardcodeExpenseService implements ExpenseService {

    private List<Expense> expenseList;

    private static HardcodeExpenseService instance = new HardcodeExpenseService();

    public static HardcodeExpenseService getInstance(){
        return instance;
    }

    private HardcodeExpenseService(){
        String expenseGroup = UUID.randomUUID().toString();
        expenseList = Arrays.asList(
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
        expenseList = Collections.synchronizedList(expenseList);
    }

    public List<Expense> getExpenses() {
        return expenseList;
    }

    public Expense getExpenseById(int id){
        final int VALUE_TO_CONVERT_TO_INDEX = 1;

        int index = id-VALUE_TO_CONVERT_TO_INDEX;
        if((index<0)||(index>=expenseList.size())){
            return null;
        }
        return expenseList.get(index);
    }
}
