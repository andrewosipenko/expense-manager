package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.constants.Constants;
import com.es.jointexpensetracker.exception.ExpenseGroupNotFoundException;
import com.es.jointexpensetracker.model.PersonExpense;
import com.es.jointexpensetracker.service.DebtService;
import com.es.jointexpensetracker.utils.ExpenseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StatisticsServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DebtService debtService = DebtService.getInstance();
            String expenseGroupId = ExpenseUtil.getExpenseGroupIdFromRequest(request);
            List<PersonExpense> personExpenseList = debtService.getPersonExpenseList(expenseGroupId);
            request.setAttribute("people" , personExpenseList);
            request.setAttribute("debtors", debtService.getDebtors(expenseGroupId));
            request.getRequestDispatcher("WEB-INF/pages/statistics.jsp").forward(request, response);
        } catch (ExpenseGroupNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
