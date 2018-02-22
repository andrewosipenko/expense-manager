package com.es.jointexpensetracker.web.servlet;

import com.es.jointexpensetracker.service.ExpenseService;
import com.es.jointexpensetracker.web.service.ExpenseGroupUUIDService;
import com.es.jointexpensetracker.web.service.MessageService;
import com.es.jointexpensetracker.web.service.URLpatternHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ExpenseGroupServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            HttpSession session = req.getSession(true);
            String uuid = URLpatternHandler.getUUIDfromURI(req.getRequestURI());
            if (!ExpenseService.getInstance().containsGroup(uuid))
            {
                MessageService.sendMessage(req, MessageService.FLASH_MESSAGE,
                        "Seems like you've used invalid group id or haven't already created it");
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            else
            {
                session.setAttribute(ExpenseGroupUUIDService.UUID, uuid);
                req.getRequestDispatcher(URLpatternHandler.getURLafterUUID(req)).forward(req, resp);
            }
        }
        catch (StringIndexOutOfBoundsException e)
        {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            req.getRequestDispatcher(URLpatternHandler.getURLafterUUID(req)).forward(req, resp);
        }
        catch (StringIndexOutOfBoundsException e)
        {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

