package com.loo.tp.controllers;
import org.javatuples.Triplet;

import com.loo.tp.entities.User;
import com.loo.tp.session.SessionManager;

public abstract class BaseController {
    protected final String USER_IS_NOT_ADMIN_ERROR_MESSAGE = "Usuario no es administrador.";

    protected SessionManager sessionManager;

    public BaseController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    protected <T> Triplet<Boolean, T, String> Ok(T t) {
        return new Triplet<Boolean, T, String>(true, t, null);
    }

    protected <T> Triplet<Boolean, T, String> Error(String str) {
        return new Triplet<Boolean, T, String>(false, null, str);
    }

    protected User getCurrentUser() {
        return sessionManager.getUser();
    }

    protected long getCurrentUserId() {
        var currentUser = this.getCurrentUser();
        return currentUser != null
                ? currentUser.getId()
                : 0;
    }

    protected boolean isAdmin() {
        var currentUser = this.getCurrentUser();
        return currentUser != null && currentUser.isAdmin();
    }
}
