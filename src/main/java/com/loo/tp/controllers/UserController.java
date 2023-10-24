package com.loo.tp.controllers;

import org.javatuples.Pair;

import com.loo.tp.entities.User;
import com.loo.tp.repository.interfaces.UserRepository;

public class UserController extends BaseController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Pair<Boolean, User> getUser(String userName, String password) {
        User user = userRepository.getByCredentials(userName, password);
        return user != null
            ? Ok(user)
            : Error(null);
    }
}

