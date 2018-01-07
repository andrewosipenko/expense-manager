package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.impl.HardcodeExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseServlet extends HttpServlet {

    private ExpenseService expenseService;

    @Override
    public void init() throws ServletException {
        super.init();
        expenseService = HardcodeExpenseService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int idExpense = getIdExpenseFromRequest(request);

            Expense expense = expenseService.getExpenseById(idExpense);

            if(expense == null){
                request.setAttribute(
                        "message", "Expense not found =("
                );
            }
            else {
                request.setAttribute(
                        "expense", expense
                );
            }
        }catch(NullPointerException e){
            request.setAttribute(
                    "message", "Expense not found =("
            );
        }catch(NumberFormatException e){
            request.setAttribute(
                    "message", "Expense not found =("
            );
        }
        request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: update expense
    }

    private int getIdExpenseFromRequest(HttpServletRequest request){

        final int SKIP_SLASH_VALUE = 1;

        String pathInfo = request.getPathInfo();
        if(pathInfo == null){
            throw new NullPointerException();
        }

        String idExpense = pathInfo.substring(SKIP_SLASH_VALUE);

        return Integer.parseUnsignedInt(idExpense);
    }
}
