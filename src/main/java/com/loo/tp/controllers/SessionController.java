package com.loo.tp.controllers;

import com.loo.tp.entities.User;
import com.loo.tp.session.SessionManager;

public class SessionController extends BaseController {
    public SessionController(SessionManager sessionManager) {
        super(sessionManager);
    }

    public boolean isLoggedIn() {
        return sessionManager.getUser() != null;
    }

    public User getCurrentUser() {
        return super.getCurrentUser();
    }

    public long getCurrentUserId() {
        return super.getCurrentUserId();
    }

    public void Logout() {
        sessionManager.setUser(null);
    }
}
