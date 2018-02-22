package com.es.jointexpensetracker.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public final class FlashMessageService {
    private static final FlashMessageService instance = new FlashMessageService();
    private static final String SESSION_FLASH_MESSAGES_ATTR = "flashMessageNames";
    private static final String SESSION_LOCK_ATTR = "sessionLock";
    private static final Object globalLock = new Object();
    public static final String DEFAULT_MESSAGE_NAME = "flashMessage";

    private FlashMessageService() {}

    private Object getSessionLock(HttpSession session) {
        Object lock;
        if ((lock = session.getAttribute(SESSION_LOCK_ATTR)) != null)
            return lock;
        synchronized (globalLock) {
            if ((lock = session.getAttribute(SESSION_LOCK_ATTR)) == null) {
                lock = new Object();
                session.setAttribute(SESSION_LOCK_ATTR, lock);
            }
        }
        return lock;
    }

    public void putFlashMessage(HttpServletRequest request, String name, Object message) throws IllegalStateException, IllegalArgumentException {
        HttpSession session = request.getSession();
        if (name == null)
            throw new IllegalArgumentException("Message name mustn't be null");
        if (message == null)
            throw new IllegalArgumentException("Message value mustn't be null");
        if (session == null)
            throw new IllegalArgumentException("Session object mustn't be null");
        synchronized (getSessionLock(session)) {
            Object messagesObj = session.getAttribute(SESSION_FLASH_MESSAGES_ATTR);
            if (messagesObj == null) {
                messagesObj = new MessageMap();
                session.setAttribute(SESSION_FLASH_MESSAGES_ATTR, messagesObj);
            } else if (!(messagesObj instanceof MessageMap))
                throw new IllegalStateException("Session already has a flash message map attribute of wrong type");
            MessageMap messageNames = (MessageMap) messagesObj;
            messageNames.put(name, message);
        }
    }

    public void putFlashMessage(HttpServletRequest request, Object message) {
        putFlashMessage(request, DEFAULT_MESSAGE_NAME, message);
    }

    public boolean forwardFlashMessages(HttpServletRequest request) throws IllegalStateException, IllegalArgumentException {
        HttpSession session = request.getSession();
        if (session == null)
            throw new IllegalArgumentException("Session object mustn't be null");
        synchronized (getSessionLock(session)) {
            Object messagesObj = session.getAttribute(SESSION_FLASH_MESSAGES_ATTR);
            session.removeAttribute(SESSION_FLASH_MESSAGES_ATTR);
            if (messagesObj == null)
                return false;
            if (messagesObj instanceof MessageMap) {
                MessageMap messages = (MessageMap) messagesObj;
                messages.forEach(request::setAttribute);
                return !messages.isEmpty();
            } else
                throw new IllegalStateException("Session already has a flash message map attribute of wrong type");
        }
    }

    public static FlashMessageService getInstance() {
        return instance;
    }

    private static class MessageMap extends HashMap<String, Object> {} // avoid unchecked class cast to Map<String, Object>
}
