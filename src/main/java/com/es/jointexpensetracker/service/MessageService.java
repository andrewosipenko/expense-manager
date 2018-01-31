package com.es.jointexpensetracker.service;

import com.es.jointexpensetracker.constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MessageService {

    private static volatile MessageService messageService;

    public static  MessageService getInstance(){
        if(messageService == null){
            synchronized (MessageService.class) {
                if(messageService == null) {
                    messageService = new MessageService();
                }
            }
        }
        return messageService;
    }

    public void clearMessage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String message = (String) session.getAttribute(Constants.MESSAGE_ATTRIBUTE);
            request.setAttribute(Constants.MESSAGE_ATTRIBUTE, message);
            session.removeAttribute(Constants.MESSAGE_ATTRIBUTE);
        }
    }

    public void setMessage(HttpServletRequest request, String message) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.setAttribute(Constants.MESSAGE_ATTRIBUTE, message);
        }
    }
}
