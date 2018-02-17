package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.FlashMessageService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FlashMessageFilter implements Filter {
    private FlashMessageService messageService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        messageService = FlashMessageService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        boolean hasMessages = messageService.forwardFlashMessages(request);
        if (hasMessages)
            request.setAttribute("defaultMessageName", FlashMessageService.DEFAULT_MESSAGE_NAME);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
