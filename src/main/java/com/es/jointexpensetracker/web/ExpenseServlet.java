package com.es.jointexpensetracker.web;


import com.es.jointexpensetracker.service.ExpenseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpenseServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if(pathInfo.matches("/[1-9]+[0-9]*")) {
            Integer id = Integer.parseInt(pathInfo.substring(1));
            request.setAttribute("expense", ExpenseService.getInstance().getOne(id));
            request.getRequestDispatcher("/WEB-INF/pages/expense.jsp").forward(request, response);
        }
        else {
            throw new IllegalArgumentException("Request path mismatch");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: update expense
    }
}
