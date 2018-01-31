package com.es.jointexpensetracker.web;


import com.es.jointexpensetracker.constants.Constants;
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
import java.util.concurrent.ConcurrentHashMap;


public class ExpenseServlet extends HttpServlet {

    private ExpenseService expenseService;
    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();

        expenseService = ExpenseService.getInstance();
        messageService = MessageService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     try {
       Long id = ExpenseUtil.getId(request);

        request.setAttribute("expense", expenseService.getExpense(id).get());
        request.setAttribute("currencies", Currency.getAvailableCurrencies());
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
     }  catch (InvalidPathException | NoSuchElementException e) {
         String errorMessage = e.getMessage();
         if(errorMessage != null)
              request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE,errorMessage);
         response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
     }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = ExpenseUtil.getId(request);

            if(request.getParameter("update") != null) {
                onUpdate(request,response,id);
            } else if (request.getParameter("delete") != null) {
                onDelete(request,response,id);
            }
        } catch (InvalidPathException | ExpenseNotFoundException e) {
            String errorMessage = e.getMessage();
            if(errorMessage != null)
                request.setAttribute(Constants.ERROR_MESSAGE_ATTRIBUTE,errorMessage);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void onUpdate(HttpServletRequest request,HttpServletResponse response, Long id) throws IOException, ServletException, InvalidPathException, ExpenseNotFoundException {

        Expense expense;
        Optional<Expense> expenseOptional = ExpenseUtil.getValidExpense(request, response);
        if(expenseOptional.isPresent()) {
            expense = expenseOptional.get();
            expenseService.updateExpense(expense);
            messageService.setMessage(request,"Expense  "+expense.getDescription()+" was updated successfully ");

            response.sendRedirect(request.getContextPath()+"/expenses");
        } else {
            request.setAttribute("expense", expenseService.getExpense(id).orElseThrow(ExpenseNotFoundException::new));
            request.setAttribute("currencies", Currency.getAvailableCurrencies());
            messageService.setMessage(request, "Check the data for valid input ");
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
        }
    }

    private void onDelete(HttpServletRequest request, HttpServletResponse response, Long id) throws IOException, ServletException, ExpenseNotFoundException {

        Optional<Expense> expense = expenseService.getExpense(id);
        if(expense.isPresent()) {
            expenseService.delete(id);
            messageService.setMessage(request,"Expense  "+expense.get().getDescription()+" was deleted successfully");
            response.sendRedirect(request.getContextPath()+"/expenses");
        }  else {
            throw new ExpenseNotFoundException("Expense to be deleted does not exist");
        }
    }
}
