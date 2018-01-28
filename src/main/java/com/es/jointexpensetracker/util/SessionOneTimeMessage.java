package com.es.jointexpensetracker.util;

import javax.servlet.http.HttpSession;

public class SessionOneTimeMessage {
    private HttpSession session;
    private String name;
    private String message;

    public SessionOneTimeMessage(HttpSession session, String name, String message) {
        this.session = session;
        this.name = name;
        this.message = message;
    }

    public void set(){
        session.setAttribute(name, this);
    }

    public String extractMessage(){
        session.removeAttribute(name);
        return message;
    }
}
