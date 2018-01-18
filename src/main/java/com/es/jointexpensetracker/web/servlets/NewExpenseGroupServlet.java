package com.es.jointexpensetracker.web.servlets;

import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.impl.HardcodeExpenseService;
import com.es.jointexpensetracker.web.services.MessageService;
import com.es.jointexpensetracker.web.services.impl.SessionMessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewExpenseGroupServlet extends AbstractExpenseServlet {

    private ExpenseService expenseService;

    @Override
    public void init() throws ServletException {
        super.init();
        expenseService = HardcodeExpenseService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String expenseGroupId = expenseService.createNewExpenseGroup();
        response.sendRedirect(request.getContextPath()+"/expense-groups/"+expenseGroupId+"/expenses");
    }
}
