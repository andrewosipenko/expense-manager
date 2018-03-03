package com.es.jointexpensetracker.filter;

import com.es.jointexpensetracker.constants.Constants;
import com.es.jointexpensetracker.exception.ExpenseGroupNotFoundException;
import com.es.jointexpensetracker.exception.ServletPathException;
import com.es.jointexpensetracker.utils.RequestSplitUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
       try {
           HttpServletRequest request = (HttpServletRequest) req;
           String path = RequestSplitUtil.getServletPathFromRequest(request);
           String expenseGroupId = RequestSplitUtil.getExpenseGroupIdFromRequest(request);
           request.setAttribute(Constants.EXPENSE_GROUP_ID,expenseGroupId);
           request.getRequestDispatcher(path).forward(req,resp);
       } catch (ExpenseGroupNotFoundException | ServletPathException e) {
           HttpServletResponse response = (HttpServletResponse)resp;
           response.sendError(HttpServletResponse.SC_NOT_FOUND);
       }
    }
    public void init(FilterConfig config) throws ServletException {
    }

}
