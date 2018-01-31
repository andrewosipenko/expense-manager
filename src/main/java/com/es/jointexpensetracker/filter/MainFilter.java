package com.es.jointexpensetracker.filter;

import com.es.jointexpensetracker.constants.Constants;
import com.es.jointexpensetracker.service.MessageService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MainFilter implements Filter {
    private MessageService messageService;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        messageService.clearMessage(request);
        chain.doFilter(req, resp);

    }

    public void init(FilterConfig config) throws ServletException {

        messageService = MessageService.getInstance();
    }

}
