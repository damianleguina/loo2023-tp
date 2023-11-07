package com.loo.tp.controllers;

import org.javatuples.Pair;

import com.loo.tp.entities.User;
import com.loo.tp.repository.interfaces.UserRepository;
import com.loo.tp.session.SessionManager;

public class UserController extends BaseController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository, SessionManager sessionManager) {
        super(sessionManager);
        this.userRepository = userRepository;
    }

    public Pair<Boolean, User> getUser(String userName, String password) {
        User user = userRepository.getByCredentials(userName, password);
        if (user != null) {
            sessionManager.setUser(user);
            return Ok(user);
        }
        return Error(null);
    }

    public Pair<Boolean, User[]> getUsers() {
        if (!this.isAdmin()) {
            return Error(new User[0]);
        }
        return Ok(userRepository.get());
    }

    public Pair<Boolean, User> getById(long userId) {
        if (!this.isAdmin()) {
            return Error(null);
        }
        var user = userRepository.getById(userId);
        return user != null
                ? Ok(user)
                : Error(null);
    }

    public Pair<Boolean, User> changeStatus(long userId, boolean status) {
        if (!this.isAdmin()) {
            return Error(null);
        }
        var user = userRepository.changeStatus(userId, status);
        return user != null
                ? Ok(user)
                : Error(null);
    }
}
