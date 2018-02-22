package com.es.jointexpensetracker.utils;

import com.es.jointexpensetracker.exception.ExpenseGroupNotFoundException;
import com.es.jointexpensetracker.exception.ServletPathException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestSplitUtil {
    private static String getSplitUri(HttpServletRequest request) throws ExpenseGroupNotFoundException {
        String requestURI = request.getRequestURI();

        String[] splittedUriInfo = (requestURI.split("expense-groups/"));
        if(splittedUriInfo.length < 2)
            throw new ExpenseGroupNotFoundException();
        return splittedUriInfo[1];
    }

    public static String getExpenseGroupIdFromRequest(HttpServletRequest request) throws ExpenseGroupNotFoundException {
        String splitUri = getSplitUri(request);
        String expenseGroupId = splitUri.substring(0, splitUri.indexOf("/"));
        if(expenseGroupId.equals(""))
            throw new ExpenseGroupNotFoundException();
       return expenseGroupId;
    }

    public static String getServletPathFromRequest(HttpServletRequest request) throws ExpenseGroupNotFoundException, ServletPathException {
        String splitUri = getSplitUri(request);
        String path = splitUri.substring(splitUri.indexOf("/"));
        if(path.equals(""))
            throw new ServletPathException();
        return path;
    }
}
