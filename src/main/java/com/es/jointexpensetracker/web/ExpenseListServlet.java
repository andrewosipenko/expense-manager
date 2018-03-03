package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.exception.ExpenseGroupNotFoundException;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.utils.ExpenseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String expenseGroupId = ExpenseUtil.getExpenseGroupIdFromRequest(request);
            if(expenseGroupId == null)
                throw new ExpenseGroupNotFoundException();
            request.setAttribute("expenses", ExpenseService.getInstance().getExpensesByGroupId(expenseGroupId));
            request.getRequestDispatcher("WEB-INF/pages/expenses.jsp").forward(request, response);
        } catch (ExpenseGroupNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);        }
    }
}
