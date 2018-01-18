package com.es.jointexpensetracker.web.servlets;


import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.exceptions.ExpenseGroupNotFoundException;
import com.es.jointexpensetracker.service.impl.HardcodeExpenseService;
import com.es.jointexpensetracker.web.exceptions.HttpNotFoundException;
import com.es.jointexpensetracker.web.services.MessageService;
import com.es.jointexpensetracker.web.services.impl.SessionMessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddExpenseServlet extends AbstractExpenseServlet {

    private MessageService messageService;
    private ExpenseService expenseService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = SessionMessageService.getInstance();
        expenseService = HardcodeExpenseService.getInstance();
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
        try {
            String expenseGroupId = loadExpenseGroupId(request);
            Expense expense = new Expense();

            if (updateExpenseFromRequest(expense,request)) {
                expense.setExpenseGroup(expenseGroupId);
                expenseService.addNewExpense(expense);
                messageService.setMessage(request, "Expense \"" + expense.getDescription() + "\" was created successfully");
                response.sendRedirect(request.getContextPath()+"/expense-groups/"+expenseGroupId+"/expenses");
            } else {
                request.setAttribute(
                        "message", "Please, check input data"
                );
                request.setAttribute(
                        "showAddForm" , true
                );
                request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
            }
        }catch (ExpenseGroupNotFoundException e){
            throw new HttpNotFoundException(e);
        }
    }
}
