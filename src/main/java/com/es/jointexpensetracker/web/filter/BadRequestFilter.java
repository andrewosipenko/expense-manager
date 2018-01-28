package com.es.jointexpensetracker.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BadRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad request");
    }

    @Override
    public void destroy() {

    }
}
