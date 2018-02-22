package com.es.jointexpensetracker.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ExpenseGroupUUIDService
{
    public static final String UUID_START_FLAG = "expense-group";
    public static final String UUID = "UUID";

    public static String getCurrentUUID (HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        return (String) session.getAttribute(UUID);
    }

    public static String getFlagWithCurrentUUID (HttpServletRequest request)
    {
        return "/" + UUID_START_FLAG + "/" + getCurrentUUID(request);
    }

}
