package com.es.jointexpensetracker.filter;

import com.es.jointexpensetracker.web.service.ExpenseGroupUUIDService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorrectURIFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if (!uri.contains(ExpenseGroupUUIDService.UUID_START_FLAG))
        {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        else
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy()
    {

    }
}
