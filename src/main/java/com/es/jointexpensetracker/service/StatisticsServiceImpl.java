package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.model.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsServiceImpl implements StatisticsService{

    @Override
    public Map<String, BigDecimal> getChartInfo(List<Expense> expenses){
        return expenses.stream()
                .collect(Collectors.toMap(
                        (a) -> String.format("'%s'", a.getPerson()),
                        Expense::getAmount,
                        BigDecimal::add,
                        LinkedHashMap::new
                ));
    }

    @Override
    public List<Debt> getDebts(List<Expense> expenses){
        List<String> names = getMembersNames(expenses);
        BigDecimal namesAmount = BigDecimal.valueOf(names.size());
        List<Debt> debts = createDebtList(names);

        expenses.forEach(e -> {
                    BigDecimal value = e.getAmount().divide(namesAmount, 2, RoundingMode.HALF_EVEN);
                    debts.forEach(debt -> debt.processIncome(e.getPerson(), value));
                });
        return debts.stream()
                .filter(d->d.getAmount().compareTo(BigDecimal.ZERO)!=0).
                collect(Collectors.toList());
    }

    private List<String> getMembersNames(List<Expense> expenses){
        return expenses.stream()
                .map(Expense::getPerson)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Debt> createDebtList(List<String> names){
        int size = names.size();
        List<Debt> debtList = new LinkedList<>();
        for(int i = 0; i < size; i++){
            for(int j = i+1; j < size; j++){
                debtList.add(new Debt(names.get(i), names.get(j)));
            }
        }
        return debtList;
    }
}
