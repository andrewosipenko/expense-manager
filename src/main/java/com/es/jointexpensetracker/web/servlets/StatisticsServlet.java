package com.es.jointexpensetracker.web.servlets;


import com.es.jointexpensetracker.model.Debt;
import com.es.jointexpensetracker.service.StatisticService;
import com.es.jointexpensetracker.service.exceptions.ExpenseGroupNotFoundException;
import com.es.jointexpensetracker.service.impl.StatisticServiceImpl;
import com.es.jointexpensetracker.web.exceptions.HttpNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class StatisticsServlet extends AbstractExpenseServlet {

    private StatisticService statisticService;

    @Override
    public void init() throws ServletException {
        super.init();
        statisticService = StatisticServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String expenseGroupId = loadExpenseGroupId(request);

            Map<String,BigDecimal> expensesMap = statisticService.loadExpensesMapByExpenseGroupId(expenseGroupId);
            List<Debt> debtList = statisticService.loadDebtsByExpenseGroupId(expenseGroupId);

            request.setAttribute(
                    "persons" , expensesMap.keySet()
            );
            request.setAttribute(
                    "amounts" , expensesMap.values()
            );
            request.setAttribute(
                    "debts" , debtList
            );

            request.getRequestDispatcher("/WEB-INF/pages/statistics.jsp").forward(request, response);
        }catch (ExpenseGroupNotFoundException e){
            throw new HttpNotFoundException(e);
        }
    }
}
