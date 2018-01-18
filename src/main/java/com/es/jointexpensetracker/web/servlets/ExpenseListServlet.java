package com.es.jointexpensetracker.web.servlets;

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

public class ExpenseListServlet extends AbstractExpenseServlet {

    private MessageService messageService;
    private ExpenseService expenseService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = SessionMessageService.getInstance();
        expenseService = HardcodeExpenseService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            String expenseGroupId = loadExpenseGroupId(request);
            request.setAttribute(
                "expenses", expenseService.loadExpensesGroupById(expenseGroupId).getExpenseList()
            );
            request.getRequestDispatcher("/WEB-INF/pages/expenses.jsp").forward(request, response);

            messageService.clearMessage(request);
        }catch (ExpenseGroupNotFoundException e){
            throw new HttpNotFoundException(e);
        }
    }
}
