package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.FlashMessageService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FlashMessageFilter implements Filter {
    private FlashMessageService service;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        service = FlashMessageService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        service.forwardFlashMessages(request);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
