package com.es.jointexpensetracker.web.servlet;

import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.web.service.ExpenseGroupUUIDService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

public class HomePageServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String uuid = UUID.randomUUID().toString();
        ExpenseService.getInstance().addExpenseGroup(uuid);
        HttpSession session = req.getSession(true);
        session.setAttribute(ExpenseGroupUUIDService.UUID, uuid);
        resp.sendRedirect(req.getContextPath() + ExpenseGroupUUIDService.getFlagWithCurrentUUID(req) + "/expenses");
    }
}
