package com.es.jointexpensetracker.web.servlets;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.impl.HardcodeExpenseService;
import com.es.jointexpensetracker.web.exceptions.HttpNotFoundException;
import com.es.jointexpensetracker.web.services.MessageService;
import com.es.jointexpensetracker.web.services.impl.SessionMessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ExpenseServlet extends CommonExpenseServlet {

    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = SessionMessageService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExpenseService expenseService = loadExpenseService(request);

        int idExpense = getIdExpenseFromRequest(request);

        Expense expense = expenseService
                .getExpenseById(idExpense)
                .orElseThrow(HttpNotFoundException::new);
        request.setAttribute(
                "expense", expense
        );

        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExpenseService expenseService = loadExpenseService(request);

        int idExpense = getIdExpenseFromRequest(request);

        Optional<Expense> expenseOptional = expenseService
                                        .getExpenseById(idExpense);
        String message = null;
        if (request.getParameter("update") != null) {
            Expense expense = expenseOptional
                            .orElseThrow(HttpNotFoundException::new);

            if (!updateExpenseByRequest(expense, request)) {
                request.setAttribute(
                        "message", "Please, check input data"
                );
                request.setAttribute(
                        "expense", expense
                );
                request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
                return;
            }
            message = "Expense \"" + expense.getDescription() + "\" was update successfully";
        } else if (request.getParameter("delete") != null) {
            expenseService.deleteExpenseById(idExpense);
            Expense expense = expenseOptional
                        .orElseGet(null);
            if(expense == null){
                message = "Expense was delete successfully";
            }else{
                message = "Expense \"" + expense.getDescription() + "\" was delete successfully";
            }
        }
        else{
            throw new HttpNotFoundException();
        }
        messageService.setMessage(request, message);
        response.sendRedirect(request.getContextPath() + "/expenses");
    }

    private int getIdExpenseFromRequest(HttpServletRequest request){
        try {
            final int SKIP_SLASH_VALUE = 1;

            String pathInfo = request.getPathInfo();

            String idExpense = pathInfo.substring(SKIP_SLASH_VALUE);

            return Integer.parseUnsignedInt(idExpense);
        }catch(NullPointerException | NumberFormatException e){
            throw new HttpNotFoundException(e);
        }
    }
}
