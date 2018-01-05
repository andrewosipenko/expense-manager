package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.exception.DataNotFoundException;
import com.es.jointexpensetracker.model.Expense;
import com.es.jointexpensetracker.service.ExpenseServiceSingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class ExpenseServlet extends HttpServlet {

    private final static String EXPENSE_JSP_PATH = "/WEB-INF/pages/expense.jsp";
    private final static String PATH_REGEX = "/[1-9][0-9]*";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if(Pattern.matches(PATH_REGEX, pathInfo)){
            long expenseId = Long.parseLong(pathInfo.substring(1));
            try {
                request.setAttribute(
                        "expense",
                        ExpenseServiceSingleton.getInstance().loadExpenseById(expenseId)
                );
                request.getRequestDispatcher(EXPENSE_JSP_PATH).forward(request, response);
            } catch (DataNotFoundException e){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if(Pattern.matches(PATH_REGEX, pathInfo)) {
            long expenseId = Long.parseLong(pathInfo.substring(1));
            try {
                Expense expense = ExpenseServiceSingleton.getInstance().loadExpenseById(expenseId);
                expense.setAmount(
                        new BigDecimal(request.getParameter("amount"))
                );
                expense.setDescription(
                        request.getParameter("description")
                );
                expense.setPerson(
                        request.getParameter("person")
                );
                expense.setDate(
                        LocalDate.parse(request.getParameter("date"))
                );
                ExpenseServiceSingleton.getInstance().save();

                response.sendRedirect(getServletContext().getContextPath()+"/expenses");
            } catch (DataNotFoundException e){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
