package com.es.jointexpensetracker.service;

import javax.servlet.http.HttpServletRequest;

public interface NotificationService {

    String MESSAGE_KEY = "com.es.jointexpensetracker.service.NotificationService.MESSAGE";

    void attachMessage(HttpServletRequest request, String message);

}
