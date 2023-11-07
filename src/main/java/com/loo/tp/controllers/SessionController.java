package com.loo.tp.controllers;

import com.loo.tp.entities.User;
import com.loo.tp.session.SessionManager;

public class SessionController {
    private SessionManager sessionManager;

    public SessionController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean isLoggedIn() {
        return sessionManager.getUser() != null;
    }

    public User getCurrentUser() {
        return sessionManager.getUser();
    }

    public void Logout() {
        sessionManager.setUser(null);
    }
}
