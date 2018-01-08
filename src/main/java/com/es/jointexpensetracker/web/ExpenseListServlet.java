package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.service.impl.ExpenseServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ExpenseListServlet extends HttpServlet {

    private ExpenseService expenseService;

    @Override
    public void init() throws ServletException {
        super.init();
        expenseService = ExpenseServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute(
            "expenses", expenseService.getExpenses()
        );
        request.getRequestDispatcher("/WEB-INF/pages/expenses.jsp").forward(request, response);

        HttpSession session = request.getSession(false);
        if(session != null){
            String message = (String)session.getAttribute("infoMessage");
            if(message != null){
                session.removeAttribute("infoMessage");
            }
        }
    }
}
