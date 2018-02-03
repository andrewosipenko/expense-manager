package com.es.jointexpensetracker.service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FlashMessageService {
    private static final FlashMessageService instance = new FlashMessageService();
    private static final String SESSION_FLASH_MESSAGES_ATTR = "flashMessageNames";
    private static final String SESSION_LOCK_ATTR = "sessionLock";
    private static final Object globalLock = new Object();

    private FlashMessageService(){}

    private Object getSessionLock(HttpSession session){
        Object lock;
        if ((lock = session.getAttribute(SESSION_LOCK_ATTR)) != null)
            return lock;
        synchronized (globalLock){
            if ((lock = session.getAttribute(SESSION_LOCK_ATTR)) == null){
                lock = new Object();
                session.setAttribute(SESSION_LOCK_ATTR, lock);
            }
        }
        return lock;
    }

    public void putFlashMessage(HttpSession session, String name, Object message) throws IllegalStateException, IllegalArgumentException{
        if (name == null || message == null)
            throw new IllegalArgumentException("Message name and value mustn't be null-strings");
        if (session == null)
            throw new IllegalArgumentException("Session object mustn't be null");
        synchronized (getSessionLock(session)){
            Object messageNamesObj = session.getAttribute(SESSION_FLASH_MESSAGES_ATTR);
            if (messageNamesObj == null){
                messageNamesObj = new MessageNameList();
                session.setAttribute(SESSION_FLASH_MESSAGES_ATTR, messageNamesObj);
            } else if (!(messageNamesObj instanceof MessageNameList))
                throw new IllegalStateException("Session already has a flash message list attribute of wrong type");
            MessageNameList messageNames = (MessageNameList) messageNamesObj;
            session.setAttribute(name, message);
            messageNames.add(name);
        }
    }

    public Map<String, Object> getFlashMessages(HttpSession session) throws IllegalStateException, IllegalArgumentException{
        if (session == null)
            throw new IllegalArgumentException("Session object mustn't be null");
        synchronized (getSessionLock(session)){
            Object messageNamesObj = session.getAttribute(SESSION_FLASH_MESSAGES_ATTR);
            if (messageNamesObj == null)
                return Collections.emptyMap();
            else if (messageNamesObj instanceof MessageNameList) {
                MessageNameList messageNames = (MessageNameList) messageNamesObj;
                Map<String, Object> messages = new HashMap<>();
                messageNames.forEach(name -> {
                    Object val = session.getAttribute(name);
                    session.removeAttribute(name);
                    messages.put(name, val);
                });
                messageNames.clear();
                return messages;
            } else
                throw new IllegalStateException("Session already has a flash message list attribute of wrong type");
        }
    }

    public static FlashMessageService getInstance() {
        return instance;
    }

    private static class MessageNameList extends ArrayList<String> {} // avoid unchecked class cast to List<String>
}
