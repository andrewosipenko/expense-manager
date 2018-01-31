package com.es.jointexpensetracker.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FlashScopeFilter implements Filter
{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException,ServletException
    {
        if (servletRequest instanceof HttpServletRequest)
        {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            HttpSession session = request.getSession(false);
            if (session!=null)
            {
                String attributeName  = "flashMessage";
                String flashMsg = (String)session.getAttribute(attributeName);
                if (flashMsg!=null)
                {
                    servletRequest.setAttribute(attributeName,flashMsg);
                    session.removeAttribute(attributeName);
                }
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig)
    {

    }

    @Override
    public void destroy()
    {

    }
}
