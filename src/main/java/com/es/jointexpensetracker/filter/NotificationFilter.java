package com.es.jointexpensetracker.filter;

import com.es.jointexpensetracker.service.NotificationService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NotificationFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        filterChain.doFilter(servletRequest, servletResponse);

        String message = (String) request.getAttribute(NotificationService.MESSAGE_KEY);
        if(message != null){
            session.setAttribute("message", message);
        } else {
            session.removeAttribute("message");
        }
    }

    @Override
    public void destroy() {

    }
}
