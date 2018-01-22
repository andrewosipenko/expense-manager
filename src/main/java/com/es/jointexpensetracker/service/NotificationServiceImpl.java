package com.es.jointexpensetracker.service;

import javax.servlet.http.HttpServletRequest;

public class NotificationServiceImpl implements NotificationService {

    @Override
    public void attachMessage(HttpServletRequest request, String message) {
        request.setAttribute(MESSAGE_KEY, message);
    }
}
