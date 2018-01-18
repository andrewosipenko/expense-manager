package com.es.jointexpensetracker.web.services.impl;

import com.es.jointexpensetracker.web.Constants;
import com.es.jointexpensetracker.web.services.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionMessageService implements MessageService {

    private static SessionMessageService instance = new SessionMessageService();

    public static SessionMessageService getInstance(){
        return instance;
    }

    private SessionMessageService(){

    }

    @Override
    public String getMessage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String message = null;
        if(session != null){
            message = (String)session.getAttribute(Constants.INFO_MESSAGE_ATTRIBUTE_NAME);
        }
        return message;
    }

    @Override
    public void clearMessage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            String message = (String)session.getAttribute(Constants.INFO_MESSAGE_ATTRIBUTE_NAME);
            if(message != null){
                session.removeAttribute(Constants.INFO_MESSAGE_ATTRIBUTE_NAME);
            }
        }
    }

    @Override
    public void setMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute(Constants.INFO_MESSAGE_ATTRIBUTE_NAME, message);
    }
}
