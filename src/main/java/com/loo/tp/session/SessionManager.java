package com.loo.tp.session;

import com.loo.tp.entities.User;

public abstract class SessionManager {
    protected User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
