package com.es.jointexpensetracker.web;

import com.es.jointexpensetracker.service.FlashMessageService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class FlashMessageFilter implements Filter {
    private FlashMessageService service;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        service = FlashMessageService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        Map<String, Object> messages = service.getFlashMessages(session);
        messages.forEach(request::setAttribute);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
