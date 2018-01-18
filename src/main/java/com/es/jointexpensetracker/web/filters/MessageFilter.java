package com.es.jointexpensetracker.web.filters;

import com.es.jointexpensetracker.web.Constants;
import com.es.jointexpensetracker.web.exceptions.HttpNotFoundException;
import com.es.jointexpensetracker.web.services.MessageService;
import com.es.jointexpensetracker.web.services.impl.SessionMessageService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MessageFilter implements Filter {

    private MessageService messageService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        messageService = SessionMessageService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
        messageService.clearMessage((HttpServletRequest)servletRequest);
    }

    @Override
    public void destroy() {}
}
