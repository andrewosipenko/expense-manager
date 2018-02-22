package com.es.jointexpensetracker.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MessageService {
    public static final String FLASH_MESSAGE = "flashMessage";

    public static void sendMessage(HttpServletRequest request, String messageType, String message)
    {
        HttpSession session = request.getSession(true);
        session.setAttribute(messageType, message);
    }
}
