package com.es.jointexpensetracker.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NotificationFilter implements Filter{

    public static final String MESSAGE_KEY = "com.es.jointexpensetracker.filter.NotificationFilter.MESSAGE";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String message = (String) request.getAttribute(MESSAGE_KEY);
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
