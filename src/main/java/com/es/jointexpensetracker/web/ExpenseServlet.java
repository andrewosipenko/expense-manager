package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.SessionMessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch(getOperatedExpenseType(request)){
            case EXISTING:
                request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch(getOperatedExpenseType(request)){
            case EXISTING:
                UpdateExpenseFormParser parser = new UpdateExpenseFormParser(request);
                if (parser.isValid()){
                    Expense expense = (Expense) request.getAttribute("expense");

                    expense.setCurrency(parser.getCurrency());
                    expense.setDate(parser.getDate());
                    expense.setPerson(parser.getPerson());
                    expense.setDescription(parser.getDescription());
                    expense.setAmount(parser.getAmount());

                    SessionMessageService service = SessionMessageService.getInstance();
                    service.putFlashMessage(request.getSession(), "updateSuccessMessage", "Expense '"+expense.getDescription()+"' has been updated successfully");
                    response.sendRedirect(request.getContextPath() + "/expenses");
                } else {
                    request.setAttribute("updateErrorMessage", parser.getErrorMessage());
                    request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private ExpenseType getOperatedExpenseType(HttpServletRequest request){
        String path = request.getPathInfo().substring(1);
        if (path.matches("[1-9]\\d{0,18}")){
            Expense expense = null;
            try{
                long id = Long.parseLong(path);
                expense = ExpenseService.getInstance().getExpenseById(id);
            } catch (NumberFormatException ignored) {}
            if (expense != null) {
                request.setAttribute("expense", expense);
                return ExpenseType.EXISTING;
            }
        }
        return ExpenseType.UNKNOWN;
    }

    private enum ExpenseType{ EXISTING, /*NEW,*/ UNKNOWN }
}
