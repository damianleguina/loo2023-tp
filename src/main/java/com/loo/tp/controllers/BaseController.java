package com.loo.tp.controllers;

import org.javatuples.Pair;

import com.loo.tp.entities.User;
import com.loo.tp.session.SessionManager;

public abstract class BaseController {
    protected SessionManager sessionManager;

    public BaseController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    protected <T> Pair<Boolean, T> Ok(T t) {
        return new Pair<Boolean, T>(true, t);
    }

    protected <T> Pair<Boolean, T> Error(T t) {
        return new Pair<Boolean, T>(false, t);
    }

    protected User getCurrentUser() {
        return sessionManager.getUser();
    }

    protected boolean isAdmin() {
        var currentUser = this.getCurrentUser();
        return currentUser != null && currentUser.isAdmin();
    }
}
