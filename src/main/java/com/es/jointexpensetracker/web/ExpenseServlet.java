package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.constants.Constants;
import com.es.jointexpensetracker.exception.ExpenseGroupNotFoundException;
import com.es.jointexpensetracker.exception.ExpenseNotFoundException;
import com.es.jointexpensetracker.exception.InvalidPathException;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.MessageService;
import com.es.jointexpensetracker.utils.ExpenseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Currency;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ExpenseServlet extends HttpServlet {
    private ExpenseService expenseService;
    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = MessageService.getInstance();
        expenseService = ExpenseService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String expenseGroupId = ExpenseUtil.getExpenseGroupIdFromRequest(request);
            if (!isAddRequest(request)) {
                Long id = ExpenseUtil.getId(request);
                Optional<Expense> requestedExpense = expenseService.getExpense(id, expenseGroupId);
                if (requestedExpense.isPresent())
                    request.setAttribute("expense", requestedExpense.get());
                 else
                    throw new InvalidPathException();
            }
        } catch (InvalidPathException | NoSuchElementException | ExpenseGroupNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        request.setAttribute("currencies", Currency.getAvailableCurrencies());
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String expenseGroupId = (String)request.getAttribute(Constants.EXPENSE_GROUP_ID);
            Long id = ExpenseUtil.getId(request);
            if (request.getParameter("delete") != null) {
                    onDelete(request, response, id, expenseService, expenseGroupId);
            } else  {
                    onEdit(request, response, id, expenseService, expenseGroupId);
                }
        } catch(InvalidPathException | ExpenseNotFoundException | ExpenseGroupNotFoundException e){
            String errorMessage = e.getMessage();
            if (errorMessage != null)
                request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE, errorMessage);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void onEdit(HttpServletRequest request, HttpServletResponse response, Long id, ExpenseService expenseService, String expenseGroupId) throws IOException, ServletException,
            InvalidPathException, ExpenseNotFoundException, ExpenseGroupNotFoundException {
        Optional<Expense> expenseOptional = ExpenseUtil.getValidExpense(request);
        if(expenseOptional.isPresent()) {
            Expense expense = expenseOptional.get();
            if (request.getParameter("update") != null) {
                expenseService.updateExpense(expense, expenseGroupId);
                messageService.setMessage(request,"Expense  " + expense.getDescription() + " was updated successfully ");
            } else if (isAddRequest(request)) {
                expenseService.add(expense, expenseGroupId);
                messageService.setMessage(request,"Expense  " + expense.getDescription() + " was created successfully ");
            }
            response.sendRedirect(request.getContextPath() +"/expense-groups/" + expenseGroupId +  "/expenses");
        } else {
            request.setAttribute("expense", expenseService.getExpense(id, expenseGroupId).orElse(null));
            request.setAttribute("currencies", Currency.getAvailableCurrencies());
            messageService.setMessage(request, "Check the data for valid input ");
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
        }
    }

    private void onDelete(HttpServletRequest request, HttpServletResponse response, Long id, ExpenseService expenseService, String expenseGroupId) throws IOException, ServletException,
            ExpenseNotFoundException, ExpenseGroupNotFoundException {
        Optional<Expense> expense = expenseService.getExpense(id, expenseGroupId);
        if(expense.isPresent()) {
            expenseService.delete(id, expenseGroupId);
            messageService.setMessage(request,"Expense  "+expense.get().getDescription()+" was deleted successfully");
            response.sendRedirect(request.getContextPath() +"/expense-groups/" + expenseGroupId +  "/expenses");
        }  else
            throw new ExpenseNotFoundException();
    }

    private static boolean isAddRequest(HttpServletRequest request) {
        return request.getPathInfo().substring(Constants.SKIP_SLASH).equals("add");
    }
}
