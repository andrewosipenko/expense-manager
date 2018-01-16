package com.es.jointexpensetracker.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewExpenseGroupServlet extends CommonExpenseServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String expenseGroup = expenseGroupService.createNewGroup();
        response.sendRedirect(request.getContextPath()+"/expense-groups/"+expenseGroup+"/expenses");
    }
}
