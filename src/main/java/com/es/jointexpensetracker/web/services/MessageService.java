package com.es.jointexpensetracker.web.services;

import javax.servlet.http.HttpServletRequest;

public interface MessageService {
    String getMessage(HttpServletRequest request);
    void clearMessage(HttpServletRequest request);
    void setMessage(HttpServletRequest request, String message);
}
