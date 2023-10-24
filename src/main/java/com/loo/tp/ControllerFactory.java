package com.loo.tp;

import com.loo.tp.controllers.UserController;
import com.loo.tp.repository.UserRepositoryImpl;
import com.loo.tp.repository.interfaces.UserRepository;

public final class ControllerFactory {
    private static UserRepository userRepository;

    public static UserController getUserController() {
        if (userRepository == null) {
            userRepository = new UserRepositoryImpl();
        }
        return new UserController(userRepository);
    } 
}
