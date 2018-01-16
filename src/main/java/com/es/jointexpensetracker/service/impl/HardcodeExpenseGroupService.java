package com.es.jointexpensetracker.service.impl;

import com.es.jointexpensetracker.service.ExpenseGroupService;
import com.es.jointexpensetracker.service.ExpenseService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HardcodeExpenseGroupService implements ExpenseGroupService {

    private static HardcodeExpenseGroupService instanse = new HardcodeExpenseGroupService();

    private Map<String,ExpenseService> expenseServiceMap;

    private HardcodeExpenseGroupService(){
        Map<String,ExpenseService> expenseServiceMap = new HashMap<>();
        ExpenseService hardcodeService = new HardcodeExpenseService();
        expenseServiceMap.put(hardcodeService.getExpenseGroup(),hardcodeService);
        this.expenseServiceMap = Collections.synchronizedMap(expenseServiceMap);
    }

    public static HardcodeExpenseGroupService getInstanse(){
        return instanse;
    }

    @Override
    public ExpenseService getServiceByGroup(String expenseGroup) {
        if(expenseGroup == null){ return null; }
        return expenseServiceMap.get(expenseGroup.toLowerCase());
    }

    @Override
    public String createNewGroup() {
        String newExpenseGroup = UUID.randomUUID().toString().toLowerCase();
        expenseServiceMap.put(newExpenseGroup,new MemoryExpenseService(newExpenseGroup));
        return newExpenseGroup;
    }
}
