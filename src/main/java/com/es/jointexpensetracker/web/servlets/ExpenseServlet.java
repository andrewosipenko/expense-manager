package com.es.jointexpensetracker.web.servlets;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.exceptions.ExpenseGroupNotFoundException;
import com.es.jointexpensetracker.service.impl.HardcodeExpenseService;
import com.es.jointexpensetracker.web.Constants;
import com.es.jointexpensetracker.web.exceptions.HttpNotFoundException;
import com.es.jointexpensetracker.web.services.MessageService;
import com.es.jointexpensetracker.web.services.impl.SessionMessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ExpenseServlet extends AbstractExpenseServlet {

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
        try {
            String expenseGroupId = loadExpenseGroupId(request);

            int expenseId = getExpenseIdFromRequest(request);

            Expense expense = expenseService
                    .getExpenseById(expenseId,expenseGroupId)
                    .orElseThrow(HttpNotFoundException::new);
            request.setAttribute(
                    "expense", expense
            );

            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);

        }catch (ExpenseGroupNotFoundException e){
            throw new HttpNotFoundException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String expenseGroupId = loadExpenseGroupId(request);

            int expenseId = getExpenseIdFromRequest(request);

            Optional<Expense> expenseOptional = expenseService
                                            .getExpenseById(expenseId,expenseGroupId);
            String message;
            if (request.getParameter("update") != null) {
                Expense expense = expenseOptional
                                .orElseThrow(HttpNotFoundException::new);
                if (!updateExpense(expense, request, response)) {
                    return;
                }
                message = "Expense \"" + expense.getDescription() + "\" was updated successfully";
            } else if (request.getParameter("delete") != null) {
                expenseService.deleteExpenseById(expenseId,expenseGroupId);
                message = getExpenseDeleteMessage(expenseOptional);
            }
            else{
                throw new HttpNotFoundException();
            }
            messageService.setMessage(request, message);
            response.sendRedirect(request.getContextPath()+"/expense-groups/"+expenseGroupId+"/expenses");
        }catch (ExpenseGroupNotFoundException e){
            throw new HttpNotFoundException(e);
        }
    }

    private boolean updateExpense(Expense expense,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if (!updateExpenseFromRequest(expense, request)) {
            request.setAttribute(
                    "message", "Please, check input data"
            );
            request.setAttribute(
                    "expense", expense
            );
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
            return false;
        }
        return true;
    }

    private String getExpenseDeleteMessage(Optional<Expense> expenseOptional){
        if(expenseOptional.isPresent()){
            return "Expense \"" + expenseOptional.get().getDescription() + "\" was deleted successfully";
        }else{
            return "Expense was delete successfully";
        }
    }

    private int getExpenseIdFromRequest(HttpServletRequest request){
        try {
            final int SKIP_SLASH_VALUE = 1;

            String pathInfo = request.getPathInfo();

            if(pathInfo == null){
                throw new NullPointerException();
            }

            String expenseId = pathInfo.substring(SKIP_SLASH_VALUE);

            return Integer.parseUnsignedInt(expenseId);
        }catch(NullPointerException | NumberFormatException e){
            throw new HttpNotFoundException(e);
        }
    }
}
