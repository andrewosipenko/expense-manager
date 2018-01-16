package com.es.jointexpensetracker.web.servlets;


import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.impl.HardcodeExpenseService;
import com.es.jointexpensetracker.web.services.MessageService;
import com.es.jointexpensetracker.web.services.impl.SessionMessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddExpenseServlet extends CommonExpenseServlet {

    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = SessionMessageService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(
                "showAddForm" , true
        );
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExpenseService expenseService = loadExpenseService(request);
        String expenseGroup = (String)request.getAttribute("expenseGroup");
        Expense expense = new Expense();

        if (updateExpenseByRequest(expense,request)) {
            expenseService.addNewExpense(expense);
            messageService.setMessage(request, "Expense \"" + expense.getDescription() + "\" was created successfully");
            response.sendRedirect(request.getContextPath()+"/expense-groups/"+expenseGroup+"/expenses");
        } else {
            request.setAttribute(
                    "message", "Please, check input data"
            );
            request.setAttribute(
                    "showAddForm" , true
            );
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
        }
    }
}
